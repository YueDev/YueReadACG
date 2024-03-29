package com.womeiyouyuming.android.yuereadacg.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Yue on 2020/1/9.
 */
object RetrofitClient {

    private const val BASE_URL_NEWS = "https://acg.178.com/"
    private const val BASE_URL_ANIME = "https://gitee.com/qweszxc9160/"
    private const val BASE_URL_GALLERY = "https://amlyu.com/"


    private val newsRetrofit = Retrofit.Builder().baseUrl(BASE_URL_NEWS).build()
    private val animeRetrofit = Retrofit.Builder().baseUrl(BASE_URL_ANIME).addConverterFactory(GsonConverterFactory.create()).build()
    private val galleryRetrofit = Retrofit.Builder().baseUrl(BASE_URL_GALLERY).build()


    fun getNewsServices(): NewsServices = newsRetrofit.create(NewsServices::class.java)
    fun getAnimeServices(): AnimeServices = animeRetrofit.create(AnimeServices::class.java)
    fun getGalleryServices(): GalleryServices = galleryRetrofit.create(GalleryServices::class.java)


}