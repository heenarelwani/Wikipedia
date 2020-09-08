package com.demo.wikipedia.article.ArticleResponseModel

import com.google.gson.annotations.SerializedName

data class Continue(@SerializedName("imcontinue") val imcontinue : String,
                    @SerializedName("grncontinue") val grncontinue : String,
                    @SerializedName("continue") val _continue : String
)