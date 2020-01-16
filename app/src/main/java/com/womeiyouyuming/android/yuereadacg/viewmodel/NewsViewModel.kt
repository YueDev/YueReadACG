package com.womeiyouyuming.android.yuereadacg.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.model.NewsSwipe
import com.womeiyouyuming.android.yuereadacg.network.NetworkStatus
import com.womeiyouyuming.android.yuereadacg.repository.NewsRepository
import com.womeiyouyuming.android.yuereadacg.util.parseNewsList
import com.womeiyouyuming.android.yuereadacg.util.parseNewsSwipeList
import kotlinx.coroutines.*

/**
 * Created by Yue on 2020/1/9.
 */
class NewsViewModel : ViewModel() {

    private val newsRepository = NewsRepository()


    private val _networkStatusLiveData = MutableLiveData<NetworkStatus>()
    val networkStatusLiveData: LiveData<NetworkStatus> = _networkStatusLiveData

    private val _newsListLiveDate = MutableLiveData<List<News>>()
    val newsListLiveDate: LiveData<List<News>> = _newsListLiveDate

    private val _newsSwipeListLiveData = MutableLiveData<List<NewsSwipe>>()
    val newsSwipeListLiveData: LiveData<List<NewsSwipe>> = _newsSwipeListLiveData


    init {

        getNewsHomeData()

    }

    fun refresh() {
        getNewsHomeData()
    }



    private fun getNewsHomeData() {


        //用两个async同时进行2个http请求

        viewModelScope.launch {

            _networkStatusLiveData.value = NetworkStatus.LOADING

            withContext(Dispatchers.IO) {

                async {
                    try {
                        val result = newsRepository.getNews(System.currentTimeMillis()).string()
                        val list = parseNewsList(result)
                        _newsListLiveDate.postValue(list)
                        _networkStatusLiveData.postValue(NetworkStatus.SUCCESS)
                    } catch (e: Exception) {
                        _networkStatusLiveData.postValue(NetworkStatus.FAILED)
                    }
                }

                async {
                    try {
                        val result = newsRepository.getNewsSwipe().string()
                        val list = parseNewsSwipeList(result)
                        _newsSwipeListLiveData.postValue(list)
                    } catch (e: Exception) {

                    }
                }

            }
        }
    }


}