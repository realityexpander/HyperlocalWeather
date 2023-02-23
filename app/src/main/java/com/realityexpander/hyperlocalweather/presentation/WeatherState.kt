package com.realityexpander.hyperlocalweather.presentation

import com.realityexpander.hyperlocalweather.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val city: String? = null,
    val country: String? = null,
    val plusCode: String? = null
)
