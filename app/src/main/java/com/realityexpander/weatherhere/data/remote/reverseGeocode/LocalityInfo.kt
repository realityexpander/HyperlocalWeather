package com.realityexpander.weatherhere.data.remote.reverseGeocode

import kotlinx.serialization.Serializable

@Serializable
data class LocalityInfo(
    val administrative: List<Administrative>,
    val informative: List<Informative>
)