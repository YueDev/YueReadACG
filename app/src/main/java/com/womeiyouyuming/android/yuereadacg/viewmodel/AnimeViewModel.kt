package com.womeiyouyuming.android.yuereadacg.viewmodel

import android.util.Log
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import com.womeiyouyuming.android.yuereadacg.repository.AnimeRepository
import com.womeiyouyuming.android.yuereadacg.repository.datasource.AnimeDataSourceFactory

/**
 * Created by Yue on 2020/8/4.
 */
class AnimeViewModel : ViewModel(){

    private val animeRepository = AnimeRepository()

    //记录rv的位置
    var rvPosition = -1
    var rvOffset = -1



    private val animeDataSourceFactory = AnimeDataSourceFactory(animeRepository)
    val animeListLiveData = animeDataSourceFactory.toLiveData(10)
    val networkStateLiveData = Transformations.switchMap(animeDataSourceFactory.source) {
        it.networkState
    }


    fun retry() = animeDataSourceFactory.source.value?.retryFailed()

    fun refresh() = animeDataSourceFactory.source.value?.invalidate()

}