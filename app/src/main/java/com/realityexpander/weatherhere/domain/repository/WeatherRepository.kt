package com.realityexpander.weatherhere.domain.repository

import com.realityexpander.weatherhere.domain.util.Resource
import com.realityexpander.weatherhere.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}