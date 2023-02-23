package com.realityexpander.hyperlocalweather.data.remote.reverseGeocode

import kotlinx.serialization.Serializable

@Serializable
data class LocalityInfo(
    val administrative: List<Administrative>,
    val informative: List<Informative>
)