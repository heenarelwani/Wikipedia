package com.demo.wikipedia.category.categoeyResponseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Allcategory(
    @SerializedName("category")val category: String?=null
)
