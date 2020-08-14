package com.womeiyouyuming.android.yuereadacg.network

import com.womeiyouyuming.android.yuereadacg.model.Anime
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Yue on 2020/8/4.
 */

//https://gitee.com/qweszxc9160/resources/raw/master/wanjie/wanjie_1.json
//    .
//    .
//    .
//https://gitee.com/qweszxc9160/resources/raw/master/wanjie/wanjie_6.json

// baseurl = https://gitee.com/qweszxc9160/


interface AnimeServices {

    @GET("resources/raw/master/wanjie/wanjie_{page}.json")
    suspend fun getAnimeList(@Path("page")page: Int): List<Anime>
}