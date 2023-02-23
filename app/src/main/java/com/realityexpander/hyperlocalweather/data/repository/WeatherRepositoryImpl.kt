package com.realityexpander.hyperlocalweather.data.repository

import com.realityexpander.hyperlocalweather.data.mappers.toWeatherInfo
import com.realityexpander.hyperlocalweather.data.remote.WeatherApi
import com.realityexpander.hyperlocalweather.domain.repository.WeatherRepository
import com.realityexpander.hyperlocalweather.domain.util.Resource
import com.realityexpander.hyperlocalweather.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}