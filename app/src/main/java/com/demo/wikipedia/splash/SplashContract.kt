package com.demo.wikipedia.splash

interface SplashContract {
    interface SplashView{
        fun init()
    }
    interface SplashPresenter{
        fun start()
    }
}