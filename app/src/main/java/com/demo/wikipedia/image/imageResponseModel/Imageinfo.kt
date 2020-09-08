package com.demo.wikipedia.image.imageResponseModel

import com.google.gson.annotations.SerializedName

data class Imageinfo (
    @SerializedName("timestamp") val timestamp : String,
    @SerializedName("user") val user : String,
    @SerializedName("url") val url : String,
    @SerializedName("descriptionurl") val descriptionurl : String,
    @SerializedName("descriptionshorturl") val descriptionshorturl : String

)
