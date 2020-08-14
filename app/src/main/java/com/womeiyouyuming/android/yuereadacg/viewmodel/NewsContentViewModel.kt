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


    val newsContentLiveData = liveData {

        val result = try {
            val httpResult = newsRepository.getNewsContent(formattedUrl).string()
            parseNewsContent(httpResult)
        } catch (e: Exception) {
            e.printStackTrace()
            "似乎出了点问题"
        }
        emit(result)
    }


}