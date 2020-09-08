package com.demo.wikipedia.network

import com.demo.wikipedia.article.ArticleResponseModel.ArticleResponse
import com.demo.wikipedia.category.categoeyResponseModel.CategoryResponse
import com.demo.wikipedia.image.imageResponseModel.ImageResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.*

interface ApiInterface {
    @GET("api.php?action=query&list=allcategories&acprefix=List+of&formatversion=2&format=json")
    fun callCategoryWikiApi(): Call<CategoryResponse>

    @GET("api.php?action=query&list=allcategories&acprefix=List+of&formatversion=2&format=json")
    fun callContinueWikiApi(@QueryMap map: HashMap<String,String>):Call<CategoryResponse>

    @GET("api.php?action=query&prop=imageinfo&iiprop=timestamp%7Cuser%7Curl&generator=categorymembers&gcmtype=file&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&format=json")
    fun callImageWikiApi():Call<ImageResponse>

    @GET("api.php?action=query&prop=imageinfo&iiprop=timestamp%7Cuser%7Curl&generator=categorymembers&gcmtype=file&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&format=json")
    fun callImageContinueWikiApi(@QueryMap map: HashMap<String,String>):Call<ImageResponse>

    @GET("api.php?format=json&action=query&generator=random&grnnamespace=0&prop=revisions%7Cimages&rvprop=content&grnlimit=10")
    fun callArticleApi():Call<ArticleResponse>

}
