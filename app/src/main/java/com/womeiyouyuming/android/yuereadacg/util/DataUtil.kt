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
    //有的tags会加中英文的两种","，需要去掉，有的tags还是空的或者空格，也需要判断
    //178的码农水平这么烂吗
    val json = httpResult.substringAfter("var _articles=")
    val type = object : TypeToken<List<News?>>() {}.type
    return Gson().fromJson<List<News?>>(json, type).filterNotNull().map {
        val tag = it.tags.substringBefore(",").substringBefore("，").run {
            if (isBlank()) "其他" else this
        }
        it.copy(tags = tag)
    }
}


// 178给的url有的带base url，有的不带，另外有的以http开头，所以都要处理
fun formatUrl(url: String) = url.replace("http://", "https://").let {
    if (it.startsWith("https://")) it.substringAfter("https://acg.178.com/") else it.substringAfter(
        "/"
    )
}


fun parseNewsContent(httpResult: String): String? {
    val body = Jsoup.parse(httpResult)
        .select("div[class=article-main]")
        .select("div[class=article]").html()
    val start = "<html><head><meta charset=\"utf-8\"><link href=\"news_content_178acg.css\" rel=\"stylesheet\"></head><body>"
    val end = "</body></html>"

    return "$start$body$end"
}



fun getEmptyNews() = News("", "", "", "", "", "")


