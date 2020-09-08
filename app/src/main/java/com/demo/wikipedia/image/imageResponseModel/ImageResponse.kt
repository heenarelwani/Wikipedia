package com.demo.wikipedia.image.imageResponseModel

import com.google.gson.annotations.SerializedName

data class ImageResponse (
    @SerializedName("batchcomplete") val batchcomplete :String,
    @SerializedName("continue") val _continue:Continue,
    @SerializedName("query") val query :Query

)