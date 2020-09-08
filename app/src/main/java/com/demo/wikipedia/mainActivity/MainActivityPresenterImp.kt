package com.demo.wikipedia.mainActivity

import androidx.fragment.app.Fragment

class MainActivityPresenterImp(private var mainActivityView:MainActivityContract.activityView):MainActivityContract.activityPresenter {
    override fun addFragment(fragment: Fragment) {
        mainActivityView.setFragment(fragment)
    }

}