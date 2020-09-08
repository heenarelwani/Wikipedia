package com.demo.wikipedia.image.imageResponseModel

import com.google.gson.annotations.SerializedName

data class Query (
    @SerializedName("pages") val pages :HashMap<String,PageNo>

)