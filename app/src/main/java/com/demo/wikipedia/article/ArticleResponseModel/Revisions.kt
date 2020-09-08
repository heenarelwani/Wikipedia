package com.demo.wikipedia.article.ArticleResponseModel

import com.google.gson.annotations.SerializedName

data class Revisions (

    @SerializedName("contentformat") val contentformat : String,
    @SerializedName("contentmodel") val contentmodel : String,
    @SerializedName("*") val revision : String
)
