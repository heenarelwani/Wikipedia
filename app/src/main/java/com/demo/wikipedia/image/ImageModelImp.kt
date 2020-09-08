package com.demo.wikipedia.image

import android.util.Log

import com.demo.wikipedia.image.imageResponseModel.ImageResponse
import com.demo.wikipedia.image.imageResponseModel.PageNo
import com.demo.wikipedia.image.imageResponseModel.Query
import com.demo.wikipedia.network.ApiInterface
import com.demo.wikipedia.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageModelImp:ImageContract.ImageModel {
    override fun callNextPageApi(nextPage: String, onFinish: ImageContract.ImageModel.onFinish) {
        val apiInterface: ApiInterface = ApiService.createService1()!!.create(ApiInterface::class.java)
        val map= HashMap<String,String>()
        map.put("gcmcontinue",nextPage)
        val call: Call<ImageResponse> = apiInterface.callImageContinueWikiApi(map)
        call.enqueue(object :Callback<ImageResponse>{

            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if(response.code()==200){
                    val imageResponse=response.body()
                    val next_page=imageResponse?._continue?.gcmcontinue
                    val query: Query? =imageResponse?.query
                    val hashMap=query?.pages
                    println("HashMap++"+hashMap.toString())
                    onFinish.Next_finish(hashMap!!,next_page!!)
                }
            }
            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {

            }

        })
    }

    override fun callApi(onFinish: ImageContract.ImageModel.onFinish) {
        val apiInterface: ApiInterface = ApiService.createService1()!!.create(ApiInterface::class.java)
        val call: Call<ImageResponse> = apiInterface.callImageWikiApi()
        call.enqueue(object :Callback<ImageResponse>{

            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if(response.code()==200){
                    val imageResponse=response.body()
                    val next_page=imageResponse?._continue?.gcmcontinue
                    val query: Query? =imageResponse?.query
                    val hashMap=query?.pages
                    println("HashMap++"+hashMap.toString())
                    onFinish.finish(hashMap!!,next_page!!)
                }
            }
            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
            }

        })
    }
}