package com.demo.wikipedia.category.categoeyResponseModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class CategoryResponse (
    @SerializedName("batchcomplete") val batchcomplete:Boolean,
    @SerializedName("continue") val _continue:Continue,
    @SerializedName("query") val  query:Query
)
