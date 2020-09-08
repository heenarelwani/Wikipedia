package com.demo.wikipedia.category.categoeyResponseModel

import com.google.gson.annotations.SerializedName

class Query (
    @SerializedName("allcategories") val  allcategories :ArrayList<Allcategory>
)