package com.womeiyouyuming.android.yuereadacg.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import com.womeiyouyuming.android.yuereadacg.repository.GalleryRepository
import com.womeiyouyuming.android.yuereadacg.repository.datasource.GalleryDataSourceFactory

/**
 * Created by Yue on 2020/8/4.
 *
 *
 * 同animeViewModel，懒的抽取
 *
 */
class GalleryViewModel : ViewModel(){

    private val galleryRepository = GalleryRepository()

    //记录rv的位置
    var rvPosition = -1
    var rvOffset = -1



    private val galleryDataSourceFactory = GalleryDataSourceFactory(galleryRepository)
    val photoListLiveData = galleryDataSourceFactory.toLiveData(10)
    val networkStateLiveData = Transformations.switchMap(galleryDataSourceFactory.source) {
        it.networkState
    }


    fun retry() = galleryDataSourceFactory.source.value?.retryFailed()

    fun refresh() = galleryDataSourceFactory.source.value?.invalidate()

}