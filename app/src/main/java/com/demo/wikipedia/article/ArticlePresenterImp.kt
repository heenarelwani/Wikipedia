package com.demo.wikipedia.article

import android.app.Activity
import android.content.Context
import android.view.View
import com.demo.wikipedia.article.ArticleResponseModel.NoPages
import com.demo.wikipedia.database.ArticleTable
import com.demo.wikipedia.database.DatabaseHelper
import org.json.JSONArray
import org.json.JSONObject

class ArticlePresenterImp(val articleView:ArticleContract.ArticleView):ArticleContract.ArticlePresenter,ArticleContract.ArticleModel.onFinish,ArticleContract.ArticleModel.internetResult {

    private var articleModelImp:ArticleContract.ArticleModel?=null
    init {
        articleModelImp=ArticleModelImp()
    }
    override fun finish(mapValue: HashMap<String, NoPages>, nextPage: String,imcontinue:String?,jsonArray: JSONArray) {
       articleView.loadFirstPage(mapValue,nextPage,imcontinue,jsonArray)
    }

    override fun Next_finish(mapValue: HashMap<String, NoPages>, nextPage: String,imcontinue:String?,jsonArray: JSONArray) {
        articleView.loadNextPage(mapValue,nextPage,imcontinue,jsonArray)
    }

    override fun startInit(view: View) {
       articleView.init(view)
    }

    override fun doAPiNextPagecall(nextPage: String,imcontinue:String?) {
      articleModelImp?.callNextPageApi(nextPage,imcontinue,this)

    }
    override fun checkInternet(context: Context, activity: Activity,databaseHelper: DatabaseHelper) {
        articleModelImp?.checkInternet(context,activity,this,databaseHelper)
    }

    override fun onSuccess() {
        articleModelImp?.callApi(this)
    }

    override fun onFailure(arrayList: ArrayList<ArticleTable>?) {
        articleView.offlinedata(arrayList)
    }
    override fun notifyConnection(connection: Boolean) {
       articleView.notifyConnection(connection)
    }


}