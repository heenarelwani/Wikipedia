package com.demo.wikipedia.article.ArticleResponseModel

import com.google.gson.annotations.SerializedName

data class NoPages (
    @SerializedName("pageid") val pageid : Int?=null,
    @SerializedName("ns") val ns : Int?=null,
    @SerializedName("title") val title : String?=null,
    @SerializedName("revisions") val revisions : ArrayList<Revisions>?=null,
    @SerializedName("images") val images : ArrayList<Images>?=null
)
