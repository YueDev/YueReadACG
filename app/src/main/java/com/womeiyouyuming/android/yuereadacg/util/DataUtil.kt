package com.womeiyouyuming.android.yuereadacg.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.model.NewsSwipe
import org.jsoup.Jsoup

/**
 * Created by Yue on 2020/1/9.Swipe
 */
fun parseNewsSwipeList(httpResult: String) = Jsoup.parse(httpResult)
    .select("div[class=banner]>div[class=swiper-wrapper]>div[class=swiper-slide]").map {

        val imgElement = it.select("img")
        val title = imgElement.attr("alt")
        val imgUrl = imgElement.attr("src")

        val url = it.select("a").attr("href")

        NewsSwipe(title, url, imgUrl)
    }

fun parseNewsList(httpResult: String): List<News> {
    //acg.178给数据会有空数据，因此需要过滤null
    val json = httpResult.substringAfter("var _articles=")
    val type = object : TypeToken<List<News?>>() {}.type
    return Gson().fromJson<List<News?>>(json, type).filterNotNull()
}

