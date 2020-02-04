package com.womeiyouyuming.android.yuereadacg.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Yue on 2020/1/9.
 */


//newsSwipe   https://acg.178.com/m/index.html

//newsList   https://acg.178.com/api/articles.js
//  调用加上了Date.now，试了试不加也行，


interface NewsServices {

    //swipe banner

    @GET("m/index.html")
    suspend fun getNewsSwipe(): ResponseBody


    //news list
    @GET("api/articles.js")
    suspend fun getNews(@Query("v") currentTimeMillis: Long): ResponseBody

    //news content
    @GET("{url}")
    suspend fun getNewsContent(@Path("url") url :String): ResponseBody

    // 动漫情报
    // 第1页
    // https://acg.178.com/list/88134860671.html
    // 2-30页
    // https://acg.178.com/list/88134860671_30.html

    @GET("list/88134860671.html")
    suspend fun getNewsAnime(): ResponseBody
    @GET("list/88134860671_{page}.html")
    suspend fun getNewsAnime(@Path("page")page: Int): ResponseBody
}