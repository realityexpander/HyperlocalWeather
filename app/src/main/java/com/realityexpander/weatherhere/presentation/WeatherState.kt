package com.realityexpander.weatherhere.presentation

import com.realityexpander.weatherhere.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val city: String? = null,
    val country: String? = null,
    val plusCode: String? = null
)
