package com.aman.coroutineretromvvm.model

import com.google.gson.annotations.SerializedName

//Serialized name is what will be mentioned in the API response
data class Country(
    @SerializedName("name")
    val countryName: String?,
    @SerializedName("capital")
    val capital: String?,
    @SerializedName("flagPNG")
    val flag: String?
)