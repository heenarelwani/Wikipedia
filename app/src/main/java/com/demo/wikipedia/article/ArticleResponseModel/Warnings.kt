package com.demo.wikipedia.article.ArticleResponseModel

import com.google.gson.annotations.SerializedName

data class Warnings (
    @SerializedName("main") val main : Main,
    @SerializedName("revisions") val revisions : Revisions

)
