package com.womeiyouyuming.android.yuereadacg.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Yue on 2020/8/14.
 */
interface GalleryServices {

    //https://amlyu.com/page/2/


    //分页请求
    @GET("page/{page}/")
    suspend fun getPhotos(
        @Path("page")
        page: Int
    ): ResponseBody

}