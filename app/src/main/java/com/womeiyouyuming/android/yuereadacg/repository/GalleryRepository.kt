package com.womeiyouyuming.android.yuereadacg.repository

import com.womeiyouyuming.android.yuereadacg.network.RetrofitClient

/**
 * Created by Yue on 2020/8/14.
 */
class GalleryRepository {

    private val galleryServices = RetrofitClient.getGalleryServices()

    suspend fun getPhotos(page: Int) = galleryServices.getPhotos(page)

}