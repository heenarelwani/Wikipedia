package com.demo.wikipedia.image

import android.view.View
import android.widget.ProgressBar
import com.demo.wikipedia.category.CategoryContract
import com.demo.wikipedia.category.categoeyResponseModel.Allcategory
import com.demo.wikipedia.image.imageResponseModel.PageNo

class ImagePresenterImp(val imageView: ImageContract.ImageView) :ImageContract.ImagePresenter,ImageContract.ImageModel.onFinish{
    private var imageModelImp: ImageContract.ImageModel?= null
    init {
        imageModelImp=ImageModelImp()
    }
    override fun startInit(view: View) {
        imageView.init(view)
    }

    override fun doApiCall() {
       imageModelImp?.callApi(this)
    }

    override fun doAPiNextPagecall(nextPage: String) {
        imageModelImp?.callNextPageApi(nextPage,this)
    }

    override fun finish(mapValue:HashMap<String,PageNo>, nextPage: String) {
        imageView.loadFirstPage(mapValue,nextPage)
    }

    override fun Next_finish(mapValue: HashMap<String,PageNo>, nextPage: String) {
        imageView.loadNextPage(mapValue,nextPage)
    }

}