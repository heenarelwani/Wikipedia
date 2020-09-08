package com.demo.wikipedia.mainActivity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.demo.wikipedia.R
import com.demo.wikipedia.article.ArticleFragment
import com.demo.wikipedia.category.CategoryFragment
import com.demo.wikipedia.category.categoeyResponseModel.Allcategory
import com.demo.wikipedia.category.categoeyResponseModel.CategoryResponse
import com.demo.wikipedia.category.categoeyResponseModel.Query
import com.demo.wikipedia.image.ImageFragment

import com.demo.wikipedia.network.ApiInterface
import com.demo.wikipedia.network.ApiService
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(),MainActivityContract.activityView{
    private var presenterImp:MainActivityPresenterImp?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenterImp= MainActivityPresenterImp(this)
        presenterImp?.addFragment(ArticleFragment())
        bottom_navigation.setOnNavigationItemSelectedListener(object :BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId){
                    R.id.navigation_Category -> presenterImp?.addFragment(CategoryFragment())
                    R.id.navigation_Image->presenterImp?.addFragment(ImageFragment())
                    R.id.navigation_Article->presenterImp?.addFragment(ArticleFragment())
                    else-> Log.d("msg","not executed")
                }
                return false
            }
        })
    }
    override fun setFragment(fragment: Fragment) {
        val transaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}




