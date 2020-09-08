package com.demo.wikipedia.article.ArticleResponseModel

import com.google.gson.annotations.SerializedName

data class ArticleResponse (@SerializedName("continue") val _continue : Continue,
                            @SerializedName("warnings") val warnings : Warnings,
                            @SerializedName("query") val query : Query
)
