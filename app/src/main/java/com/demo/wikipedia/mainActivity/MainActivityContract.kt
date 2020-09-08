package com.demo.wikipedia.mainActivity

import androidx.fragment.app.Fragment


interface MainActivityContract {
    interface activityView{
        fun setFragment(fragment: Fragment)
    }
    interface activityPresenter{
        fun addFragment(fragment: Fragment)
    }
}