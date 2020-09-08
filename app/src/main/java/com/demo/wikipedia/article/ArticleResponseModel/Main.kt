package com.demo.wikipedia.article.ArticleResponseModel

import com.google.gson.annotations.SerializedName

data class Main (

    @SerializedName("*") val main : String
)
