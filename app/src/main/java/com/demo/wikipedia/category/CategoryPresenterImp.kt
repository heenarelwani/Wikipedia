package com.demo.wikipedia.category

import android.view.View
import com.demo.wikipedia.category.categoeyResponseModel.Allcategory

class CategoryPresenterImp(private val categoryView:CategoryContract.categoryView):CategoryContract.categoryPresenter,CategoryContract.categoryModel.onFinish {
    private var contrModelImp:CategoryContract.categoryModel?= null
    init {
        contrModelImp=CategoryModelImp()
    }
    override fun startInit(view: View) {
        categoryView.init(view)
    }

    override fun doApiCall() {
       contrModelImp?.callApi(this)

    }

    override fun doAPiNextPagecall(nextPage: String) {
      contrModelImp?.callNextPageApi(nextPage,this)
    }

    override fun finish(category: ArrayList<Allcategory>,nextPage:String) {
      categoryView.loadFirstPage(category,nextPage)
    }

    override fun Next_finish(category: ArrayList<Allcategory>, nextPage: String) {
       categoryView.loadNextPage(category,nextPage)
    }


}