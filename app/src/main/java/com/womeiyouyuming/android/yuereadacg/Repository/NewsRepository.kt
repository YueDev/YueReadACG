package com.womeiyouyuming.android.yuereadacg.Repository

import com.womeiyouyuming.android.yuereadacg.network.RetrofitClient

/**
 * Created by Yue on 2020/1/9.
 */
class NewsRepository {

    private val newsServices = RetrofitClient.getNewsServices()

    suspend fun getNewsSwipe() = newsServices.getNewsSwipe()

    suspend fun getNews(currentTimeMillis: Long) = newsServices.getNews(currentTimeMillis)


}