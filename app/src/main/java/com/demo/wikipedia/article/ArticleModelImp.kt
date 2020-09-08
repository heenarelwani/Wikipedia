package com.demo.wikipedia.article

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.widget.Toast
import com.demo.wikipedia.article.ArticleResponseModel.ArticleResponse
import com.demo.wikipedia.article.ArticleResponseModel.NoPages
import com.demo.wikipedia.article.ArticleResponseModel.Query
import com.demo.wikipedia.database.ArticleTable
import com.demo.wikipedia.database.DatabaseHelper
import com.demo.wikipedia.network.ApiInterface
import com.demo.wikipedia.network.ApiService
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ArticleModelImp:ArticleContract.ArticleModel {
    var isConnected = true
    var apiCalledFirstTym = false
    var activity: Activity? = null
    var context:Context? = null
    var imcontinue:String?=null
    var next_page:String?=null
    var query:Query?=null
    var _databaseHelper:DatabaseHelper?=null
    var hashMap:HashMap<String,NoPages>?=null
    lateinit var finalJSONObject:JSONArray
    var string:String?=null
    lateinit var internetResult: ArticleContract.ArticleModel.internetResult
    var articleDetailsModels: ArrayList<ArticleTable>? = ArrayList()
    override fun callNextPageApi(
        nextPage: String,im_continue:String?,
        onFinish: ArticleContract.ArticleModel.onFinish
    ) {
        val apiInterface: ApiInterface = ApiService.createService()!!.create(ApiInterface::class.java)
        val map= HashMap<String,String>()
        map.put("grncontinue",nextPage)
        if(im_continue!=null){
            map.put("imcontinue",im_continue)
        }
        val call: Call<ArticleResponse> = apiInterface.callArticleApi()
        call.enqueue(object :Callback<ArticleResponse>{

            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if(response.code()==200){
                    val articleResponse=response.body()
                    imcontinue=articleResponse?._continue?.imcontinue
                    next_page=articleResponse?._continue?.grncontinue
                    query=articleResponse?.query
                    hashMap=query?.pages
                    println("HashMap++"+hashMap.toString())
                   finalJSONObject=storeInJSON(hashMap)

                    onFinish.Next_finish(hashMap!!,next_page!!,imcontinue,finalJSONObject!!)
                }
            }
            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Toast.makeText(context,"Try Again", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun callApi(onFinish: ArticleContract.ArticleModel.onFinish) {

        val apiInterface: ApiInterface = ApiService.createService()!!.create(ApiInterface::class.java)
        val call: Call<ArticleResponse> = apiInterface.callArticleApi()
        call.enqueue(object : Callback<ArticleResponse> {

            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if(response.code()==200){
                    val articleResponse=response.body()
                    imcontinue=articleResponse?._continue?.imcontinue
                    next_page=articleResponse?._continue?.grncontinue
                    query=articleResponse?.query
                    hashMap=query?.pages
                    println("HashMap++"+hashMap.toString())
                    finalJSONObject=storeInJSON(hashMap)
                    onFinish.finish(hashMap!!,next_page!!,imcontinue,finalJSONObject!!)
                }
            }
            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Toast.makeText(context,"Try Again", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun storeInJSON(hashMap: HashMap<String, NoPages>?):JSONArray {
        var jsonObject:JSONObject?=null
        val jsonArray:JSONArray=JSONArray()
        val gson = Gson()
            if (hashMap != null) {
                for (value in hashMap){
                    jsonObject= JSONObject()
                    jsonObject.put("pageNo",value.key)
                    val detail=gson.toJson(value.value)
                    jsonObject.put("article_detail",detail)
                    jsonArray.put(jsonObject)
                }
            }
        return jsonArray
    }


    private val connectivityCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            activity!!.runOnUiThread(Runnable {
                if (!isConnected) {
                    internetResult.notifyConnection(true)
                    if(!apiCalledFirstTym){
                        _databaseHelper!!.delete()
                        //first time api call
                        internetResult.onSuccess()
                        apiCalledFirstTym=true
                    }

                }
            })

        }

        override fun onLost(network: Network) {
            //hideDialog()
            isConnected = false
            activity!!.runOnUiThread(Runnable {
                internetResult.notifyConnection(false)
            })
        }
    }
    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting

    }

    override fun checkInternet(_context: Context, _activity: Activity,
                               _internetResult: ArticleContract.ArticleModel.internetResult,databaseHelper: DatabaseHelper) {
        activity=_activity
        context=_context
        internetResult=_internetResult
        _databaseHelper=databaseHelper
        if (isNetworkAvailable()) {
            _databaseHelper!!.delete()
            internetResult.onSuccess()
            apiCalledFirstTym=true
            isConnected = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val connectivityManager =
                    context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                connectivityManager?.registerNetworkCallback(
                    NetworkRequest.Builder()
                        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
                    connectivityCallback
                )
            }
        } else {
            internetResult.notifyConnection(false)
            articleDetailsModels= _databaseHelper!!.getAllDetails()!!
            internetResult.onFailure(articleDetailsModels)
            isConnected = false
            apiCalledFirstTym=false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val connectivityManager =
                    context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                if (connectivityManager != null) {
                    connectivityManager.registerNetworkCallback(
                        NetworkRequest.Builder()
                            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
                        connectivityCallback
                    )
                }
            }
        }
    }


}