package com.demo.wikipedia.article

import android.app.Activity
import android.content.Context
import android.view.View
import com.demo.wikipedia.article.ArticleResponseModel.NoPages
import com.demo.wikipedia.database.ArticleTable
import com.demo.wikipedia.database.DatabaseHelper
import com.demo.wikipedia.image.imageResponseModel.PageNo
import org.json.JSONArray
import org.json.JSONObject

interface ArticleContract {
    interface ArticleView{
        fun init(view: View)
        fun loadFirstPage(mapValue:HashMap<String, NoPages>, nextPage: String,imcontinue:String?,jsonArray: JSONArray)
        fun loadNextPage(mapValue:HashMap<String, NoPages>, nextPage: String,imcontinue:String?,jsonArray: JSONArray)
        fun offlinedata(arrayList: ArrayList<ArticleTable>?)
        fun notifyConnection(connection:Boolean)

    }
    interface ArticleModel{
        fun callNextPageApi(nextPage:String,imcontinue:String?,onFinish: onFinish)
        fun callApi(onFinish: onFinish)
        interface onFinish{
            fun finish(mapValue:HashMap<String, NoPages>, nextPage:String,imcontinue:String?,jsonArray: JSONArray)
            fun Next_finish(mapValue:HashMap<String, NoPages>, nextPage:String,imcontinue:String?,jsonArray: JSONArray)
        }
        fun checkInternet(context: Context,activity: Activity,internetResult: internetResult,databaseHelper: DatabaseHelper)
        interface internetResult{
            fun onSuccess()
            fun onFailure(arrayList:ArrayList<ArticleTable>?)
            fun notifyConnection(connection:Boolean)
        }
    }
    interface ArticlePresenter{
        fun startInit(view: View)

        fun doAPiNextPagecall(nextPage: String,imcontinue:String?)
        fun checkInternet(context: Context,activity: Activity,databaseHelper: DatabaseHelper)

    }
}