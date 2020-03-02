package com.womeiyouyuming.android.yuereadacg.viewmodel

import androidx.lifecycle.*
import androidx.paging.toLiveData
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.model.NewsSwipe
import com.womeiyouyuming.android.yuereadacg.network.NetworkState
import com.womeiyouyuming.android.yuereadacg.repository.NewsRepository
import com.womeiyouyuming.android.yuereadacg.repository.datasource.*
import com.womeiyouyuming.android.yuereadacg.util.parseNewsList
import com.womeiyouyuming.android.yuereadacg.util.parseNewsSwipeList
import kotlinx.coroutines.*

/**
 * Created by Yue on 2020/1/9.
 */
class NewsViewModel : ViewModel() {

    private val newsRepository = NewsRepository()


    //首页

    private val _networkStateLiveData = MutableLiveData<NetworkState>()
    val networkStateLiveData: LiveData<NetworkState> = _networkStateLiveData

    private val _newsListLiveDate = MutableLiveData<List<News>>()
    val newsListLiveDate: LiveData<List<News>> = _newsListLiveDate

    private val _newsSwipeListLiveData = MutableLiveData<List<NewsSwipe>>()
    val newsSwipeListLiveData: LiveData<List<NewsSwipe>> = _newsSwipeListLiveData

    //动漫资讯

    private val newsAnimeDataSourceFactory = NewsAnimeDataSourceFactory(newsRepository)
    val newsAnimeListLiveData = newsAnimeDataSourceFactory.toLiveData(10)
    val newsAnimeNetworkStateLiveData =
        Transformations.switchMap(newsAnimeDataSourceFactory.source) {
            it.networkState
        }

    //展会活动
    private val newsExpoDataSourceFactory = NewsExpoDataSourceFactory(newsRepository)
    val newsExpoListLiveData = newsExpoDataSourceFactory.toLiveData(10)
    val newsExpoNetworkStateLiveData = Transformations.switchMap(newsExpoDataSourceFactory.source) {
        it.networkState
    }


    //福利社

    private val newsBeautyDataSourceFactory = NewsBeautyDataSourceFactory(newsRepository)
    val newsBeautyListLiveData = newsBeautyDataSourceFactory.toLiveData(10)
    val newsBeautyNetworkStateLiveData = Transformations.switchMap(newsBeautyDataSourceFactory.source) {
        it.networkState
    }

    //萌周边

    private val newsFigmaDataSourceFactory = NewsFigmaDataSourceFactory(newsRepository)
    val newsFigmaListLiveData = newsFigmaDataSourceFactory.toLiveData(10)
    val newsFigmaNetworkStateLiveData = Transformations.switchMap(newsFigmaDataSourceFactory.source) {
        it.networkState
    }



    //万事屋

    private val newsSpecialDataSourceFactory = NewsSpecialDataSourceFactory(newsRepository)
    val newsSpecialListLiveData = newsSpecialDataSourceFactory.toLiveData(10)
    val newsSpecialNetworkStateLiveData = Transformations.switchMap(newsSpecialDataSourceFactory.source) {
        it.networkState
    }



    //八卦谈

    private val newsBaguaDataSourceFactory = NewsBeautyDataSourceFactory(newsRepository)
    val newsBaguaListLiveData = newsBaguaDataSourceFactory.toLiveData(10)
    val newsBaguaNetworkStateLiveData = Transformations.switchMap(newsBaguaDataSourceFactory.source) {
        it.networkState
    }




    //游戏宅

    private val newsGameDataSourceFactory = NewsGameDataSourceFactory(newsRepository)
    val newsGameLiveData = newsGameDataSourceFactory.toLiveData(10)
    val newsGameNetworkStateLiveData =
        Transformations.switchMap(newsGameDataSourceFactory.source) {
            it.networkState
        }


    init {

        getNewsHomeData()

    }

    fun refreshNewsHome() {
        getNewsHomeData()
    }


    private fun getNewsHomeData() {


        //用两个async同时进行2个http请求

        viewModelScope.launch {

            _networkStateLiveData.value = NetworkState.LOADING

            withContext(Dispatchers.IO) {

                async {
                    try {
                        val result = newsRepository.getNewsHome(System.currentTimeMillis()).string()
                        val list = parseNewsList(result)
                        _newsListLiveDate.postValue(list)
                        _networkStateLiveData.postValue(NetworkState.SUCCESS)
                    } catch (e: Exception) {
                        _networkStateLiveData.postValue(NetworkState.FAILED)
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

    fun refreshNewsAnime() {
        newsAnimeDataSourceFactory.source.value?.invalidate()
    }

    fun retryNewsAnime() {
        newsAnimeDataSourceFactory.source.value?.retryFailed()
    }

    fun refreshNewsExpo() {
        newsExpoDataSourceFactory.source.value?.invalidate()
    }

    fun retryNewsExpo() {
        newsExpoDataSourceFactory.source.value?.retryFailed()
    }

    fun refreshNewsBeauty() {
        newsBeautyDataSourceFactory.source.value?.invalidate()
    }

    fun retryNewsBeauty() {
        newsBeautyDataSourceFactory.source.value?.retryFailed()
    }

    fun refreshNewsFigma() {
        newsFigmaDataSourceFactory.source.value?.invalidate()
    }

    fun retryNewsFigma() {
        newsFigmaDataSourceFactory.source.value?.retryFailed()
    }

    fun refreshNewsSpecial() {
        newsSpecialDataSourceFactory.source.value?.invalidate()
    }

    fun retryNewsSpecial() {
        newsSpecialDataSourceFactory.source.value?.retryFailed()
    }

    fun refreshNewsBagua() {
        newsBaguaDataSourceFactory.source.value?.invalidate()
    }

    fun retryNewsBagua() {
        newsBaguaDataSourceFactory.source.value?.retryFailed()
    }




    fun refreshNewsGame() {
        newsGameDataSourceFactory.source.value?.invalidate()
    }

    fun retryNewsGame() {
        newsGameDataSourceFactory.source.value?.retryFailed()
    }





}