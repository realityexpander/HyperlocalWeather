package com.realityexpander.weatherhere.data.repository

import com.realityexpander.weatherhere.data.mappers.toWeatherInfo
import com.realityexpander.weatherhere.data.remote.WeatherApi
import com.realityexpander.weatherhere.domain.repository.WeatherRepository
import com.realityexpander.weatherhere.domain.util.Resource
import com.realityexpander.weatherhere.domain.weather.WeatherInfo
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