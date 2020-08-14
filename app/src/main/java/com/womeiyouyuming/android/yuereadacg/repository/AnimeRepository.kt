package com.womeiyouyuming.android.yuereadacg.repository

import com.womeiyouyuming.android.yuereadacg.network.RetrofitClient

/**
 * Created by Yue on 2020/8/4.
 */
class AnimeRepository {

    private val animeServices = RetrofitClient.getAnimeServices()

    suspend fun getAnimeList(page: Int) = animeServices.getAnimeList(page)

}