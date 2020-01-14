package com.womeiyouyuming.android.yuereadacg.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.womeiyouyuming.android.yuereadacg.repository.NewsRepository
import com.womeiyouyuming.android.yuereadacg.util.parseNewsList
import com.womeiyouyuming.android.yuereadacg.util.parseNewsSwipeList
import kotlinx.coroutines.Dispatchers

/**
 * Created by Yue on 2020/1/9.
 */
class NewsViewModel : ViewModel() {

    private val newsRepository = NewsRepository()

    val newsSwipeListLiveData = liveData(Dispatchers.IO) {

        val httpResult = newsRepository.getNewsSwipe().string()
        val newsSwipeList = parseNewsSwipeList(httpResult)

        emit(newsSwipeList)

    }


    val newsListLiveDate = liveData(Dispatchers.IO) {

        val httpResult = newsRepository.getNews(System.currentTimeMillis()).string()
        val newsList = parseNewsList(httpResult)
        emit(newsList)

    }




}