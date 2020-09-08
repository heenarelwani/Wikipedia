package com.demo.wikipedia.image

import android.view.View
import android.widget.ProgressBar
import com.demo.wikipedia.category.categoeyResponseModel.Allcategory
import com.demo.wikipedia.image.imageResponseModel.PageNo

interface ImageContract {
    interface ImageView{
        fun init(view: View)
        fun loadFirstPage(mapValue:HashMap<String,PageNo>,nextPage: String)
        fun loadNextPage(mapValue:HashMap<String,PageNo>,nextPage: String)

    }
    interface ImageModel{
        fun callNextPageApi(nextPage:String,onFinish: onFinish)
        fun callApi(onFinish: onFinish)
        interface onFinish{
            fun finish(mapValue:HashMap<String,PageNo>,nextPage:String)
            fun Next_finish(mapValue:HashMap<String,PageNo>,nextPage:String)
        }
    }
    interface ImagePresenter{
        fun startInit(view: View)
        fun doApiCall()
        fun doAPiNextPagecall(nextPage: String)
    }
}