package com.realityexpander.hyperlocalweather.domain.repository

import com.realityexpander.hyperlocalweather.domain.util.Resource
import com.realityexpander.hyperlocalweather.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}