package com.demo.wikipedia.category

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.wikipedia.R
import com.demo.wikipedia.category.categoeyResponseModel.Allcategory
import kotlinx.android.synthetic.main.fragment_category.view.*


class CategoryFragment: Fragment(),CategoryContract.categoryView {
   lateinit var pb_view: ProgressBar
    lateinit var rv_view: RecyclerView

    private val PAGE_START = 1
    private var _isLoading = false
    private var _isLastPage = false

    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private val TOTAL_PAGES = 5
    private var currentPage = PAGE_START
    var itemList:ArrayList<Allcategory>?=null
    lateinit var categoryItemAdapter: CategoryItemAdapter
    lateinit var nextPage: String
    private var categoryPresenterImp:CategoryPresenterImp?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_category, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryPresenterImp=CategoryPresenterImp(this)
        categoryPresenterImp?.startInit(view)
    }

    override fun init(view: View) {
        rv_view=view.main_recycler
        pb_view=view.main_progress
        categoryItemAdapter=CategoryItemAdapter(context!!, ArrayList())
        val layoutManager=LinearLayoutManager(context)
        rv_view.layoutManager=layoutManager
        rv_view.adapter=categoryItemAdapter
        rv_view.addOnScrollListener(object:PaginationScrollListener(layoutManager){
            override fun loadMoreItems() {
                _isLoading = true
                currentPage += 1
                // mocking network delay for API call
                Handler().postDelayed(Runnable {
                    categoryPresenterImp?.doAPiNextPagecall(nextPage)
                    }, 1000)
            }

            override val totalPageCount: Int
                get() = TOTAL_PAGES
            override val isLastPage: Boolean
                get() = _isLastPage
            override val isLoading: Boolean
                get() = _isLoading
        })
        categoryPresenterImp?.doApiCall()

    }

    override fun loadFirstPage(category: ArrayList<Allcategory>,next_Page:String) {
        nextPage=next_Page
        pb_view.setVisibility(View.GONE);
        categoryItemAdapter.addAll(category);

        if (currentPage <= TOTAL_PAGES) categoryItemAdapter.addLoadingFooter();
        else _isLastPage = true
    }

    override fun loadNextPage(category: ArrayList<Allcategory>, next_Page: String) {
        nextPage=next_Page
        categoryItemAdapter.removeLoadingFooter()
        _isLoading = false
        categoryItemAdapter.addAll(category)
        if (currentPage != TOTAL_PAGES) categoryItemAdapter.addLoadingFooter();
        else _isLastPage = true
    }


}