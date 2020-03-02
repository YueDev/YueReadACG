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

    //news content
    @GET("{url}")
    suspend fun getNewsContent(@Path("url") url :String): ResponseBody

    //资讯——首页
    @GET("api/articles.js")
    suspend fun getNewsHome(@Query("v") currentTimeMillis: Long): ResponseBody

    //资讯——首页——bannner

    @GET("m/index.html")
    suspend fun getNewsSwipe(): ResponseBody





    // 动漫情报
    // 第1页
    // https://acg.178.com/list/88134860671.html
    // 2-30页
    // https://acg.178.com/list/88134860671_30.html


    @GET("list/88134860671.html")
    suspend fun getNewsAnime(): ResponseBody
    @GET("list/88134860671_{page}.html")
    suspend fun getNewsAnime(@Path("page")page: Int): ResponseBody



    //展会活动
    //https://acg.178.com/list/manzhan/index.html
    //https://acg.178.com/list/manzhan/index_30.html

    @GET("list/manzhan/index.html")
    suspend fun getNewsExpo(): ResponseBody
    @GET("list/manzhan/index_{page}.html")
    suspend fun getNewsExpo(@Path("page")page: Int): ResponseBody

    //福利社
    //hot tags: 福利
    //https://acg.178.com/list/262377910211.html
    //https://acg.178.com/list/262377910211_5.html
    @GET("list/262377910211.html")
    suspend fun getNewsBeauty(): ResponseBody
    @GET("list/262377910211_{page}.html")
    suspend fun getNewsBeauty(@Path("page")page: Int): ResponseBody

    //萌周边
    // hot tags：手办
    //https://acg.178.com/list/goods/index.html
    //https://acg.178.com/list/goods/index_30.html
    @GET("list/goods/index.html")
    suspend fun getNewsFigma(): ResponseBody
    @GET("list/goods/index_{page}.html")
    suspend fun getNewsFigma(@Path("page")page: Int): ResponseBody

    //万事屋 最大15页 7页之后的内容是旧版内容，不显示
    // hot tags：宅科学 在前线
    //https://acg.178.com/list/84509538487.html
    //https://acg.178.com/list/84509538487_7.html
    @GET("list/84509538487.html")
    suspend fun getNewsSpecial(): ResponseBody
    @GET("list/84509538487_{page}.html")
    suspend fun getNewsSpecial(@Path("page")page: Int): ResponseBody


    //八卦谈
    // hot tags：八卦
    //https://acg.178.com/list/bagua/index.html
    //https://acg.178.com/list/bagua/index_30.html

    @GET("list/bagua/index.html")
    suspend fun getNewsBagua(): ResponseBody
    @GET("list/bagua/index_{page}.html")
    suspend fun getNewsBagua(@Path("page")page: Int): ResponseBody




    //游戏宅
    //第一页
    //https://acg.178.com/list/262377171368.html
    //2-30
    //https://acg.178.com/list/262377171368_30.html

    @GET("list/262377171368.html")
    suspend fun getNewsGame(): ResponseBody
    @GET("list/262377171368_{page}.html")
    suspend fun getNewsGame(@Path("page")page: Int): ResponseBody




}