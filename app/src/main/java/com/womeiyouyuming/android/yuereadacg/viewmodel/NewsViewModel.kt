package com.womeiyouyuming.android.yuereadacg.viewmodel

import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.womeiyouyuming.android.yuereadacg.Repository.NewsRepository
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.util.parseNewsList
import com.womeiyouyuming.android.yuereadacg.util.parseNewsSwipeList
import kotlinx.coroutines.Dispatchers
import java.util.*

/**
 * Created by Yue on 2020/1/9.
 */
class NewsViewModel : ViewModel() {

    private val newsRepository = NewsRepository()

    val newsSwipeListLiveData = liveData(Dispatchers.IO) {

        val httpResult = newsRepository.getNewsSwipe().string()
        val newsSwipeList = parseNewsSwipeList(httpResult)

        Log.d("YUEDEV", newsSwipeList.toString())

        emit(newsSwipeList)
    }


    val newsListLiveDate = liveData(Dispatchers.IO) {

        val httpResult = newsRepository.getNews(System.currentTimeMillis()).string()
        val newsList = parseNewsList(httpResult)
        emit(newsList)

    }



}