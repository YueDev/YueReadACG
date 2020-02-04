package com.womeiyouyuming.android.yuereadacg.repository

import com.womeiyouyuming.android.yuereadacg.network.RetrofitClient

/**
 * Created by Yue on 2020/1/9.
 */
class NewsRepository {

    private val newsServices = RetrofitClient.getNewsServices()

    suspend fun getNewsSwipe() = newsServices.getNewsSwipe()

    suspend fun getNews(currentTimeMillis: Long) = newsServices.getNews(currentTimeMillis)

    suspend fun getNewsContent(formattedUrl: String) = newsServices.getNewsContent(formattedUrl)

    suspend fun getNewsAnime() = newsServices.getNewsAnime()

    suspend fun getNewsAnime(page: Int) = newsServices.getNewsAnime(page)


}