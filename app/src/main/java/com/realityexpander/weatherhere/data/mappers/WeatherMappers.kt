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
    val hourOfWeek: Int,  // 0-167 (hours in a week)
    val data: WeatherData,
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {

    // Map a week of hourly weatherData to Key = day of week (0-6), Value = list of hourly weatherData for that day (0-24)
    return this.time.mapIndexed { hourOfWeek, timeStr ->
        val temperature = temperatures[hourOfWeek]
        val weatherCode = weatherCodes[hourOfWeek]
        val windSpeed = windSpeeds[hourOfWeek]
        val pressure = pressures[hourOfWeek]
        val humidity = humidities[hourOfWeek]

        // Convert the separate array of 168 hours (24hrs x 7days) into single hour weather objects
        IndexedWeatherData(
            hourOfWeek = hourOfWeek,
            data = WeatherData(
                time = LocalDateTime.parse(timeStr, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = ((temperature * 1.8 + 32.0) * 10).roundToInt() / 10.0, // convert to fahrenheit (rounded to 2 decimal places)
                pressure = pressure,
                windSpeed = ((windSpeed * 1.609344) * 10).roundToInt() / 10.0, // convert from km/h to mph
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.also {
        // list of weather data by the hour (168 hours)
    }.groupBy { weatherData->
        weatherData.hourOfWeek / 24  // sets the key as the day (0-6) (168 hours / 24 hours => 7 days)
    }.also {
        // key = day of the week (0-6), value = list of weather data for that day by hour (0-24)
    }.mapValues { entry -> // key = day, value = list of IndexedWeatherData for that day for each hour
        // extract the weatherData from IndexedWeatherData for each day (ie: needed to remove the "hourOfWeek" field)
        entry.value.map { weatherData -> // makes a new list of values for this day.
            weatherData.data  // extract the WeatherData object and make it the new value for this hour of the day
        } // mapValues returns a new set of values for this entry (key = day, value = new list of WeatherData for that day)
    }.also {
        // key = day of week, value = list of weather data for each hour (24)
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = this.weatherData.toWeatherDataMap()
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