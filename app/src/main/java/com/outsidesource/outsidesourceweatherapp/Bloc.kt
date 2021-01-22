package com.outsidesource.outsidesourceweatherapp


import com.outsidesource.outsidesourceweatherapp.util.Outcome
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger

/**
 * Bloc (Business Logic Component)
 * A composable block of observable state and functionality to immutably update state.
 *
 * Bloc Lifecycle
 * A Bloc's lifecycle is dependent on its observers. When the first observer observes the Bloc [onStart] is called.
 * When the last observer stops observing [onDispose] is called. A Bloc may choose to reset its state when [onDispose]
 * is called by setting [persistStateOnDispose] to false. A Bloc will call [onStart] again if it gains a new
 * observer after it has been disposed. Likewise, a Bloc will call [onDispose] again if it loses those observers.
 *
 * Observing State
 * When an observer observes state it will immediately receive the latest state as the first emit. Afterwards, only
 * changes to the state will be emitted to observers.
 *
 * Updating Bloc State
 * The only way to update a Bloc's state is by calling the [update] method. Calling [update] will synchronously update
 * the internal state with a new copy of state and notify all observers of the change as long as the new state is
 * different than the previous state.
 *
 * Bloc Effects
 * Effects are asynchronous functions that update the state over time. An effect can be created by creating
 * an asynchronous function that calls [update] multiple times or by using the [effect] method. The [effect] method
 * provides a built-in cancellation mechanism. Calling an effect multiple times will cancel the previously called
 * effect and replace it with the newly called effect. The [effect] method also allows configuring whether or not the
 * effect should be cancelled when the Bloc is disposed or not.
 *
 * [initialState] The initial state of a Bloc.
 *
 * [persistStateOnDispose] If false, the internal state will be reset to [initialState] when the bloc is
 * disposed. If true, the bloc's state will persist until the bloc is garbage collected.
 */
abstract class Bloc<T>(
    private val initialState: T,
    private val persistStateOnDispose: Boolean = false,
) {

    private val _effects: MutableMap<String, CancellableEffect<*>> = mutableMapOf()
    private val _state: MutableStateFlow<T> = MutableStateFlow(initialState)
    private var _dependentCount = AtomicInteger(0)
    private val _dependentScopes = mutableListOf<CoroutineScope>()

    /**
     * Provides a mechanism to allow launching [Job]s externally that follow the Bloc's lifecycle. All [Job]s launched
     * in [blocScope] will be cancelled when the Bloc is disposed.
     */
    val blocScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    /**
     * Retrieves the current state of the Bloc.
     */
    val state get() = _state.value

    /**
     * Returns the state as a stream/observable for observing updates. The latest state will be immediately emitted to a new subscriber.
     * Collecting the flow adds a subscription dependency to the Bloc which is removed when the Flow collector is cancelled unless a lifetimeScope is provided.
     *
     * [lifetimeScope] allows the bloc to only remove a subscription dependency when the scope has been cancelled. This prevents premature
     * Bloc disposal mainly during activity/fragment recreation due to configuration change. Typically viewModelScope is the most appropriate
     * scope here.
     */
    fun stream(lifetimeScope: CoroutineScope? = null): Flow<T> = _state.onStart { handleSubscribe(lifetimeScope) }.onCompletion { if (lifetimeScope == null) handleUnsubscribe() }

    /**
     * Called when the bloc receives its first subscription. [onStart] will be called again if it gains an
     * observer after it has been disposed.
     *
     * This is a good time to new-up any services, subscribe to dependent blocs, or open any resource handlers.
     */
    protected open fun onStart() {}

    /**
     * Called when the last subscription is closed. [onDispose] will be called every time all observers have stopped
     * observing.
     *
     * This is a good place to close any resource handlers or services.
     */
    protected open fun onDispose() {}


    /**
     * Runs a block of asynchronous code and provides a simple cancellation mechanism. If the effect is cancelled an [Outcome.Error]
     * will be returned with a [CancellationException] as its error value. When reusing ids, an ongoing effect will
     * be cancelled and the passed block will run in its place. This can prevent the issue where two of the same effect
     * are called and the first call hangs for a few seconds while the second completes more quickly.
     *
     * [cancelOnDispose] if true, the effect will be cancelled when the Bloc is disposed if the effect is still running.
     *
     * [onCancel] a block of synchronous code to be run if the effect is cancelled. This can be used to reset state if an effect is cancelled.
     */
    protected suspend fun <T> effect(id: String, cancelOnDispose: Boolean = true, onCancel: (() -> Unit)? = null, block: suspend () -> Outcome<T>): Outcome<T> = coroutineScope {
        cancelEffect(id)

        try {
            val effect = CancellableEffect(cancelOnDispose = cancelOnDispose, job = async { block() }, onCancel = onCancel)
            synchronized(_effects) { _effects[id] = effect }
            val result = effect.run()
            synchronized(_effects) { if (_effects[id] == effect) _effects.remove(id) }
            return@coroutineScope result
        } catch (e: CancellationException) {
            Outcome.Error(e)
        }
    }

    /**
     * Cancels an effect with the given [id].
     */
    protected fun cancelEffect(id: String) {
        _effects[id]?.cancel()
        synchronized(_effects) { _effects.remove(id) }
    }

    /**
     * Immutably update the state and notify all subscribers of the change.
     */
    protected fun update(block: (state: T) -> T): T {
        _state.value = block(_state.value)
        return _state.value
    }

    private fun handleSubscribe(lifetimeScope: CoroutineScope?) {
        checkShouldStart()

        if (lifetimeScope != null) {
            synchronized(_dependentScopes) {
                if (_dependentScopes.contains(lifetimeScope)) return
                _dependentScopes.add(lifetimeScope)
            }

            lifetimeScope.coroutineContext.job.invokeOnCompletion {
                synchronized(_dependentScopes) { _dependentScopes.remove(lifetimeScope) }
                CoroutineScope(Dispatchers.Default).launch { handleUnsubscribe() }
            }
        }
        _dependentCount.incrementAndGet()
        Timber.d("Bloc Dependency Added: ${this::class.qualifiedName}")
    }

    private fun handleUnsubscribe() {
        _dependentCount.decrementAndGet()
        Timber.d("Bloc Dependency Removed: ${this::class.qualifiedName}")
        checkShouldDispose()
    }

    private fun checkShouldStart() {
        if  (_dependentCount.get() > 0) return
        Timber.d("Bloc Starting: ${this::class.qualifiedName}")
        onStart()
    }

    private fun checkShouldDispose() {
        if (_dependentCount.get() > 0) return
        Timber.d("Bloc Disposed: ${this::class.qualifiedName}")

        _effects.values.forEach { if (it.cancelOnDispose) it.cancel() }
        synchronized(_effects) { _effects.clear() }
        blocScope.coroutineContext.cancelChildren()
        synchronized(_dependentScopes) { _dependentScopes.clear() }
        if (!persistStateOnDispose) _state.value = initialState
        onDispose()
    }
}

data class CancellableEffect<T>(val cancelOnDispose: Boolean, private val job: Deferred<T>, private val onCancel: (() -> Unit)?) {
    fun cancel() {
        job.cancel()
        onCancel?.invoke()
    }

    suspend fun run() = job.await()
}