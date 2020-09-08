package com.demo.wikipedia.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.demo.wikipedia.R
import com.demo.wikipedia.mainActivity.MainActivity

class SplashActivity : AppCompatActivity(),
    SplashContract.SplashView {
    val handler: Handler = Handler()
    val context:Context=this@SplashActivity
    private var presentImp: SplashContract.SplashPresenter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        presentImp=SplashPresentImp(this)
        presentImp?.start()

    }

    override fun init() {
       handler.postDelayed(Runnable {
            val intent=Intent(this@SplashActivity,MainActivity::class.java)
           context.startActivity(intent)
        }, 2000)
    }
}