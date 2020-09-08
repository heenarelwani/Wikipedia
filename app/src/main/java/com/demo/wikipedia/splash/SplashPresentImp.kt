package com.demo.wikipedia.splash

class SplashPresentImp(val splashView: SplashContract.SplashView):SplashContract.SplashPresenter {
    override fun start() {
        splashView.init()
    }
}