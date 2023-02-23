package com.realityexpander.hyperlocalweather.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.weatherDataPerDay?.get(0)?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Today",
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            val listState = rememberLazyListState()

            LaunchedEffect(state.weatherInfo.weatherDataPerDay[0]) {
                // get the index of the current hour
                val index = data.indexOfFirst {
                    it.time.hour == state.weatherInfo.currentWeatherData?.time?.hour
                }
                // scroll to the current hour
                listState.animateScrollToItem(index)
            }

            LazyRow(state = listState,
                modifier = Modifier.fillMaxWidth(),
                content = {
                items(data) { weatherData ->
                        HourlyWeatherDisplay(
                            weatherData = weatherData,
                            modifier = Modifier
                                .height(100.dp)
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            )
        }
    }
}