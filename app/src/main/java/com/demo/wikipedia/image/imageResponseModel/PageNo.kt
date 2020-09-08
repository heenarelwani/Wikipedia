package com.demo.wikipedia.image.imageResponseModel

import com.google.gson.annotations.SerializedName

data class PageNo (
    @SerializedName("pageid") val pageid : Int?=null,
    @SerializedName("ns") val ns : Int?=null,
    @SerializedName("title") val title : String?=null,
    @SerializedName("imagerepository") val imagerepository : String?=null,
    @SerializedName("imageinfo") val imageinfo : ArrayList<Imageinfo>?=null


)
