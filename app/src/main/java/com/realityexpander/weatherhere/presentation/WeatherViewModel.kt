package com.realityexpander.weatherhere.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realityexpander.weatherhere.data.remote.reverseGeocode.bigDataCloudReverseGeocodeResult
import com.realityexpander.weatherhere.domain.location.LocationTracker
import com.realityexpander.weatherhere.domain.repository.WeatherRepository
import com.realityexpander.weatherhere.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )

            locationTracker.getCurrentLocation()?.let { location ->

                getCityCountryFromLatLng(location.latitude, location.longitude).let { cityCountry ->
                    state = state.copy(
                        city = cityCountry.first,
                        country = cityCountry.second
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
                            error = result.message
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

    private val jsonDecodeLenientIgnoreUnknown = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    private suspend fun getCityCountryFromLatLng(
        lat: Double,
        long: Double
    ): Pair<String, String> {
        return withContext(Dispatchers.IO) {
            val response =
                URL(
                    "https://api.bigdatacloud.net/data/reverse-geocode-client?" +
                            "latitude=" + lat +
                            "&longitude=" + long +
                            "&localityLanguage=en"

                ).readText()

            //println(response) // leave for debug purposes

            // Get the address from the response
            val result = jsonDecodeLenientIgnoreUnknown
                .decodeFromString<bigDataCloudReverseGeocodeResult>(response)

            // Find a valid city name
            val city = result.city
            val country = result.countryName

            city to country
        }
    }
}