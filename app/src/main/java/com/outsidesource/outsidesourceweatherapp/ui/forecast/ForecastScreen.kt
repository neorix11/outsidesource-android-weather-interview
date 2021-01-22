package com.outsidesource.outsidesourceweatherapp.ui.forecast

import androidx.fragment.app.Fragment
import com.outsidesource.outsidesourceweatherapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class ForecastScreen: Fragment() {
    val viewModel: ForecastViewModel by viewModel()
}