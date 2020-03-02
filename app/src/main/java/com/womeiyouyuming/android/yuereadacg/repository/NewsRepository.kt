package com.womeiyouyuming.android.yuereadacg.repository

import com.womeiyouyuming.android.yuereadacg.network.RetrofitClient

/**
 * Created by Yue on 2020/1/9.
 */
class NewsRepository {

    private val newsServices = RetrofitClient.getNewsServices()


    //资讯页 点击后的 详情页面
    suspend fun getNewsContent(formattedUrl: String) = newsServices.getNewsContent(formattedUrl)



    //资讯——首页
    suspend fun getNewsSwipe() = newsServices.getNewsSwipe()

    suspend fun getNewsHome(currentMillis: Long) = newsServices.getNewsHome(currentMillis)

    //资讯——动漫情报
    suspend fun getNewsAnime() = newsServices.getNewsAnime()

    suspend fun getNewsAnime(page: Int) = newsServices.getNewsAnime(page)

    //资讯——展会活动
    suspend fun getNewsExpo() = newsServices.getNewsExpo()
    suspend fun getNewsExpo(page: Int) = newsServices.getNewsExpo(page)

    //资讯——福利社

    suspend fun getNewsBeauty() = newsServices.getNewsBeauty()
    suspend fun getNewsBeauty(page: Int) = newsServices.getNewsBeauty(page)


    //资讯——萌周边

    suspend fun getNewsFigma() = newsServices.getNewsFigma()
    suspend fun getNewsFigma(page: Int) = newsServices.getNewsFigma(page)


    //资讯——万事屋
    suspend fun getNewsSpecial() = newsServices.getNewsSpecial()
    suspend fun getNewsSpecial(page: Int) = newsServices.getNewsSpecial(page)


    //资讯——八卦谈

    suspend fun getNewsBagua() = newsServices.getNewsBagua()
    suspend fun getNewsBagua(page: Int) = newsServices.getNewsBagua(page)


    //资讯——游戏宅

    suspend fun getNewsGame() = newsServices.getNewsGame()
    suspend fun getNewsGame(page: Int) = newsServices.getNewsGame(page)

}