package com.realityexpander.hyperlocalweather.domain.weather

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,  // key: hour of the day, value: list of weather data for that hour
    val currentWeatherData: WeatherData?
)

