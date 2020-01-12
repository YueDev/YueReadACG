package com.womeiyouyuming.android.yuereadacg.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Yue on 2020/1/9.
 */


//newsSwipe   https://acg.178.com/m/index.html

//newsList   https://acg.178.com/api/articles.js
//  调用加上了Date.now，试了试不加也行，


interface NewsServices {


    @GET("m/index.html")
    suspend fun getNewsSwipe(): ResponseBody

    @GET("api/articles.js")
    suspend fun getNews(@Query("v") currentTimeMillis: Long): ResponseBody

}