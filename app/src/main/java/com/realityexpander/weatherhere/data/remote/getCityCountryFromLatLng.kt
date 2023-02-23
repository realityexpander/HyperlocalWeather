package com.realityexpander.weatherhere.data.remote

import com.realityexpander.weatherhere.BuildConfig
import com.realityexpander.weatherhere.data.remote.reverseGeocode.bigDataCloudReverseGeocodeResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

//private val jsonDecodeLenientIgnoreUnknown = Json {
//    isLenient = true
//    ignoreUnknownKeys = true
//}
//
//
//private suspend fun getCityCountryFromLatLng(
//    lat: Double,
//    long: Double
//): Pair<String, String> {
//    return withContext(Dispatchers.IO) {
//        val response =
//            URL(
//                "https://api.bigdatacloud.net/data/reverse-geocode-client?" +
//                        "latitude=" + lat +
//                        "&longitude=" + long +
//                        "&localityLanguage=en"
//
//            ).readText()
//
//        //println(response) // leave for debug purposes
//
//        // Get the address from the response
//        val result = jsonDecodeLenientIgnoreUnknown
//            .decodeFromString<bigDataCloudReverseGeocodeResult>(response)
//
//        // Find a valid city name
//        val city = result.city
//        val country = result.countryName
//
//        city to country
//    }
//}