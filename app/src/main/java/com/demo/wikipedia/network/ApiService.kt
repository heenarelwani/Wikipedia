package com.demo.wikipedia.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiService {
    companion object{
        val BaseURL = "https://en.wikipedia.org/w/"
        val BaseURL1="https://commons.wikimedia.org/w/"
        fun  createService(): Retrofit?{
            return Retrofit.Builder()
                .baseUrl(BaseURL)
                .client(innerClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        fun  createService1(): Retrofit?{
            return Retrofit.Builder()
                .baseUrl(BaseURL1)
                .client(innerClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        var innerClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(2, TimeUnit.MINUTES) // write timeout
            .readTimeout(2, TimeUnit.MINUTES) // read timeout
            .build()
    }
}