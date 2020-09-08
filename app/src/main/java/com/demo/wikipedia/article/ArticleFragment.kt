package com.demo.wikipedia.article

import android.graphics.ColorSpace
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.model.Model
import com.demo.wikipedia.R
import com.demo.wikipedia.article.ArticleResponseModel.NoPages
import com.demo.wikipedia.article.ArticleResponseModel.Query
import com.demo.wikipedia.category.PaginationScrollListener
import com.demo.wikipedia.database.ArticleTable
import com.demo.wikipedia.database.DatabaseHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_article.view.*

import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable


class ArticleFragment : Fragment(),ArticleContract.ArticleView {
    lateinit var linearLayout:LinearLayoutManager
    lateinit var pb_view: ProgressBar
    lateinit var rv_view: RecyclerView
    lateinit var retry:TextView
    private val PAGE_START = 1
    private var _isLoading = false
    private var _isLastPage = false
    private val TOTAL_PAGES = 5
    private var currentPage = PAGE_START
    lateinit var nextPage: String
    private  var im_continue:String?=null
    lateinit var articleItemAdapter: ArticleItemAdapter
   lateinit var databaseHelper:DatabaseHelper
    var id:Long?=null
    private var articlePresenterImp:ArticlePresenterImp?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflater=inflater.inflate(R.layout.fragment_article, container, false)
        databaseHelper=DatabaseHelper(context!!)
        return inflater
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articlePresenterImp= ArticlePresenterImp(this)
        articlePresenterImp?.startInit(view)

    }

    override fun init(view: View) {
        rv_view=view.recyclerView
        pb_view=view.progressBar
        retry=view.tv_retry
        articleItemAdapter= ArticleItemAdapter(context!!,ArrayList())
        linearLayout=LinearLayoutManager(context)
        rv_view.layoutManager=linearLayout
        rv_view.adapter=articleItemAdapter
        rv_view.addOnScrollListener(object: PaginationScrollListener(linearLayout){
            override fun loadMoreItems() {
                _isLoading = true
                currentPage += 1
                // mocking network delay for API call
                Handler().postDelayed(Runnable {
                    articlePresenterImp?.doAPiNextPagecall(nextPage,im_continue)
                }, 1000)
            }

            override val totalPageCount: Int
                get() = TOTAL_PAGES
            override val isLastPage: Boolean
                get() = _isLastPage
            override val isLoading: Boolean
                get() = _isLoading
        })
        articlePresenterImp?.checkInternet(context!!,activity!!,databaseHelper)
    }
    override fun loadFirstPage(mapValue: HashMap<String, NoPages>, next_Page: String,imcontinue:String?,jsonArray: JSONArray) {
        id= databaseHelper.insertArticleDetail(jsonArray.toString())
        nextPage=next_Page
        im_continue=imcontinue
        retry.visibility=View.GONE
        pb_view.visibility=View.GONE
        articleItemAdapter.clear()
        articleItemAdapter.addAll(mapValue)
        if (currentPage <= TOTAL_PAGES) articleItemAdapter.addLoadingFooter()
        else _isLastPage = true
    }

    override fun loadNextPage(mapValue: HashMap<String, NoPages>, next_Page: String,imcontinue:String?,jsonArray: JSONArray) {
        id= databaseHelper.insertArticleDetail(jsonArray.toString())
        nextPage=next_Page
        im_continue=imcontinue
        articleItemAdapter.removeLoadingFooter()
        _isLoading = false
        articleItemAdapter.addAll(mapValue)
        if (currentPage != TOTAL_PAGES) articleItemAdapter.addLoadingFooter()
        else _isLastPage = true
    }

    override fun offlinedata(arrayList: ArrayList<ArticleTable>?) {
        pb_view.setVisibility(View.GONE)
        val gson = Gson()
        val map:HashMap<String,NoPages> = HashMap()
        if(arrayList!!.size!=0) {
            for (result in arrayList!!) {
                val article: String =result.detail
                val jsonArray=JSONArray(article)
                println("arraylist_size"+jsonArray.toString())
               for(i in 0 until jsonArray.length())
               {
                   val jsonObject=jsonArray.getJSONObject(i)
                   val page= jsonObject.getString("pageNo")
                   println("pageNO_id"+page)
                   val detail=jsonObject.getString("article_detail")
                   println("detail_art"+detail)
                   val pages:NoPages=gson.fromJson(detail,NoPages::class.java)
                   println("modelDetail"+pages)
                   map.put(page,pages)
               }

            }
            articleItemAdapter.addAll(map)
            _isLastPage=true
        }




    }

    override fun notifyConnection(connection: Boolean) {
        if(!connection){
            retry.visibility=View.VISIBLE
        }
        else{
            retry.visibility=View.GONE
        }
    }

}