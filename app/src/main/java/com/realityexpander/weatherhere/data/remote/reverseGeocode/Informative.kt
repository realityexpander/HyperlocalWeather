package com.realityexpander.weatherhere.data.remote.reverseGeocode

import kotlinx.serialization.Serializable

@Serializable
data class Informative(
    val description: String = "",
    val geonameId: Int = -1,
    val isoCode: String = "",
    val name: String,
    val order: Int,
    val wikidataId: String = ""
)