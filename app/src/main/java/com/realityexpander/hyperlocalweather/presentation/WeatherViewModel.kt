package com.realityexpander.hyperlocalweather.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realityexpander.hyperlocalweather.data.remote.getCityCountryFromLatLng
import com.realityexpander.hyperlocalweather.domain.location.LocationTracker
import com.realityexpander.hyperlocalweather.domain.repository.WeatherRepository
import com.realityexpander.hyperlocalweather.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    val noInternetMessage = "\nNo internet connection?\n\nMake sure to enable internet and GPS."

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )

            locationTracker.getCurrentLocation()?.let { location ->

                try {
                    getCityCountryFromLatLng(
                        location.latitude,
                        location.longitude
                    ).let { cityCountryPlusCode ->
                        delay(500)
                        state = state.copy(
                            city = cityCountryPlusCode.first,
                            country = cityCountryPlusCode.second,
                            plusCode = cityCountryPlusCode.third
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    state = state.copy(
                        weatherInfo = null,
                        isLoading = false,
                        error = e.localizedMessage + noInternetMessage
                    )
                }

                when(val result = repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message + noInternetMessage
                        )
                    }
                }
            } ?: run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }
}