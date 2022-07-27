package com.realityexpander.weatherhere.data.mappers

import com.realityexpander.weatherhere.data.remote.WeatherDataDto
import com.realityexpander.weatherhere.data.remote.WeatherDto
import com.realityexpander.weatherhere.domain.weather.WeatherData
import com.realityexpander.weatherhere.domain.weather.WeatherInfo
import com.realityexpander.weatherhere.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData,
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {

    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = ((temperature * 1.8 + 32.0) * 10).roundToInt() / 10.0, // convert to fahrenheit (rounded to 2 decimal places)
                pressure = pressure,
                windSpeed = ((windSpeed * 1.609344) * 10).roundToInt() / 10.0, // convert from km/h to mph
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.also {
    }.groupBy { weatherData->
        weatherData.index / 24
    }.also {
    }.mapValues { entry ->
        entry.value.map { weatherData ->
            weatherData.data
        }
    }.also {
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()

    // Find the closest weather data to the current time
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }

    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}