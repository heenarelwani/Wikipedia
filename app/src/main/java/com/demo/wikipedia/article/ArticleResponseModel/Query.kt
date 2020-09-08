package com.demo.wikipedia.article.ArticleResponseModel

import com.google.gson.annotations.SerializedName


data class Query (

    @SerializedName("pages") val pages : HashMap<String,NoPages>
)