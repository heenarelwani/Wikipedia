package com.demo.wikipedia.image.imageResponseModel

import com.google.gson.annotations.SerializedName

data class Continue (@SerializedName("gcmcontinue") val gcmcontinue : String,
                     @SerializedName("continue") val _continue : String
)