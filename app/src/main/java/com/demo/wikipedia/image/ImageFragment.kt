package com.demo.wikipedia.image

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.wikipedia.R
import com.demo.wikipedia.category.CategoryItemAdapter
import com.demo.wikipedia.category.CategoryPresenterImp
import com.demo.wikipedia.category.PaginationScrollListener
import com.demo.wikipedia.category.categoeyResponseModel.Allcategory
import com.demo.wikipedia.image.imageResponseModel.PageNo
import kotlinx.android.synthetic.main.fragment_category.view.*
import kotlinx.android.synthetic.main.fragment_image.view.*
import kotlinx.android.synthetic.main.fragment_image.view.recyclerView


class ImageFragment : Fragment(),ImageContract.ImageView {
    lateinit var pb_view:ProgressBar
    lateinit var rv_view:RecyclerView
    private val PAGE_START = 1
    private var _isLoading = false
    private var _isLastPage = false

    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private val TOTAL_PAGES = 5
    private var currentPage = PAGE_START
    lateinit var nextPage: String
    lateinit var image_adapter:ImageItemAdapter
    private var imagePresenterImp: ImagePresenterImp?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_image, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imagePresenterImp= ImagePresenterImp(this)
        imagePresenterImp?.startInit(view)
    }
    override fun init(view: View) {
        rv_view=view.recyclerView
        pb_view=view.progressBar
        image_adapter= ImageItemAdapter(context!!,ArrayList())
        val layoutManager=LinearLayoutManager(context)
        rv_view.layoutManager=layoutManager
        rv_view.adapter=image_adapter
        rv_view.addOnScrollListener(object: PaginationScrollListener(layoutManager){
            override fun loadMoreItems() {
                _isLoading = true
                currentPage += 1
                // mocking network delay for API call
                Handler().postDelayed(Runnable {
                    imagePresenterImp?.doAPiNextPagecall(nextPage)

                }, 1000)
            }

            override val totalPageCount: Int
                get() = TOTAL_PAGES
            override val isLastPage: Boolean
                get() = _isLastPage
            override val isLoading: Boolean
                get() = _isLoading
        })
        imagePresenterImp?.doApiCall()

    }

    override fun loadFirstPage(mapValue: HashMap<String,PageNo>, next_Page: String) {
        nextPage=next_Page
        pb_view.setVisibility(View.GONE)
        image_adapter.addAll(mapValue)

        if (currentPage <= TOTAL_PAGES) image_adapter.addLoadingFooter();
        else _isLastPage = true
    }

    override fun loadNextPage(mapValue: HashMap<String,PageNo>, next_Page: String) {
        nextPage=next_Page
        image_adapter.removeLoadingFooter()
        _isLoading = false
        image_adapter.addAll(mapValue)
        if (currentPage != TOTAL_PAGES) image_adapter.addLoadingFooter()
        else _isLastPage = true
    }


}