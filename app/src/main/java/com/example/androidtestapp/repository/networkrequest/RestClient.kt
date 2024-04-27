package com.example.androidtestapp.repository.networkrequests

import com.example.androidtestapp.repository.networkrequests.WebConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestClient {
    private var REST_CLIENT: API? = null
    var retrofitInstance: Retrofit? = null

    init {
        setUpRestClient()
    }

    fun get(): API {
        return REST_CLIENT!!
    }

    private fun setUpRestClient() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttp = OkHttpClient.Builder()

        okHttp.readTimeout(120, TimeUnit.SECONDS)
        okHttp.connectTimeout(120, TimeUnit.SECONDS)
        retrofitInstance = Retrofit.Builder()
            .baseUrl(WebConstants.ACTION_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttp.build())
            .build()
        REST_CLIENT = retrofitInstance!!.create(API::class.java)
    }
}