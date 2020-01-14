package com.womeiyouyuming.android.yuereadacg.network

import retrofit2.Retrofit

/**
 * Created by Yue on 2020/1/9.
 */
object RetrofitClient {

    private const val BASE_URL_NEWS = "https://acg.178.com/"

    private val newsRetrofit = Retrofit.Builder().baseUrl(BASE_URL_NEWS).build()

    fun getNewsServices(): NewsServices = newsRetrofit.create(NewsServices::class.java)



}