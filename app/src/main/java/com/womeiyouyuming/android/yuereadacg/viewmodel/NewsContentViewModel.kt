package com.womeiyouyuming.android.yuereadacg.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.womeiyouyuming.android.yuereadacg.repository.NewsRepository
import com.womeiyouyuming.android.yuereadacg.util.parseNewsContent
import kotlinx.coroutines.Dispatchers

/**
 * Created by Yue on 2020/1/14.
 */
class NewsContentViewModel(formattedUrl: String) : ViewModel() {

    private val newsRepository = NewsRepository()




    val newsContentLiveData = liveData(Dispatchers.IO) {

        val httpResult = newsRepository.getNewsContent(formattedUrl).string()
        val result = parseNewsContent(httpResult)

        Log.d("YUEDEV", result)

        emit(result)

    }


}