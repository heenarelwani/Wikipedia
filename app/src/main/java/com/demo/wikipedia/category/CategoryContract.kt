package com.demo.wikipedia.category

import android.view.View
import com.demo.wikipedia.category.categoeyResponseModel.Allcategory

interface CategoryContract {
    interface categoryView{
        fun init(view:View)
        fun loadFirstPage(category: ArrayList<Allcategory>,nextPage: String)
        fun loadNextPage(category: ArrayList<Allcategory>,nextPage: String)
    }
    interface categoryModel{
        fun callNextPageApi(nextPage:String,onFinish: onFinish)
        fun callApi(onFinish: onFinish)
        interface onFinish{
            fun finish(category:ArrayList<Allcategory>,nextPage:String)
            fun Next_finish(category:ArrayList<Allcategory>,nextPage:String)
        }
    }
    interface categoryPresenter{
        fun startInit(view: View)
        fun doApiCall()
        fun doAPiNextPagecall(nextPage: String)
    }
}