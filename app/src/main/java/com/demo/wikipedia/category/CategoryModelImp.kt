package com.demo.wikipedia.category

import android.util.Log
import com.demo.wikipedia.category.categoeyResponseModel.Allcategory
import com.demo.wikipedia.category.categoeyResponseModel.CategoryResponse
import com.demo.wikipedia.category.categoeyResponseModel.Continue
import com.demo.wikipedia.category.categoeyResponseModel.Query
import com.demo.wikipedia.network.ApiInterface
import com.demo.wikipedia.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryModelImp:CategoryContract.categoryModel {
    override fun callNextPageApi(nextPage: String,onFinish: CategoryContract.categoryModel.onFinish) {
        val apiInterface:ApiInterface=ApiService.createService()!!.create(ApiInterface::class.java)
        val map= HashMap<String,String>()
        map.put("accontinue",nextPage)
        val call:Call<CategoryResponse> = apiInterface.callContinueWikiApi(map)
        call.enqueue(object :Callback<CategoryResponse>{


            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if(response.code()==200){
                    val categoryResponse=response.body()
                    val next_page=categoryResponse!!._continue.accontinue
                    val query:Query?=categoryResponse!!.query
                    var arrayList: ArrayList<Allcategory>?

                    arrayList=query!!.allcategories
                    println("arrayList=+"+arrayList)
                    onFinish.Next_finish(arrayList,next_page)

                }
            }
            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.d("error",t.toString())
            }
        })
    }

    override fun callApi(onFinish: CategoryContract.categoryModel.onFinish) {
       val apiInterface:ApiInterface=ApiService.createService()!!.create(ApiInterface::class.java)
        val call:Call<CategoryResponse> = apiInterface.callCategoryWikiApi()
        call.enqueue(object :Callback<CategoryResponse>{


            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
               if(response.code()==200){
                   val categoryResponse=response.body()
                   val next_page=categoryResponse!!._continue.accontinue
                   val query:Query?=categoryResponse!!.query
                   var arrayList: ArrayList<Allcategory>?

                   arrayList=query!!.allcategories
                   println("arrayList=+"+arrayList)
                   onFinish.finish(arrayList,next_page)

               }
            }
            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.d("error",t.toString())
            }
        })
    }
}