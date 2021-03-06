package com.womeiyouyuming.android.yuereadacg.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.model.NewsSwipe
import com.womeiyouyuming.android.yuereadacg.model.Photo
import org.jsoup.Jsoup

/**
 * Created by Yue on 2020/1/9.Swipe
 */


val NEWS_PAGE_LIST = listOf("首页", "动漫情报", "展会活动", "福利社", "萌周边", "万事屋", "八卦谈", "游戏宅")


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
        val tag = formatTags(it.tags)
        it.copy(tags = tag)
    }
}


fun formatTags(tags: String) = tags.substringBefore(",").substringBefore("，").run {
    if (isBlank()) "其他" else this
}

// 178给的url有的带base url，有的不带，另外有的以http开头，所以都要处理
fun formatUrl(url: String) = url.replace("http://", "https://").let {
    if (it.startsWith("https://")) it.substringAfter("https://acg.178.com/") else it.substringAfter(
        "/"
    )
}


fun parseNewsContent(httpResult: String): String {
    val body = Jsoup.parse(httpResult)
        .select("div[class=article-main]")
        .select("div[class=article]").html()
    val start =
        "<html><head><meta charset=\"utf-8\"><link href=\"news_content_178acg.css\" rel=\"stylesheet\"></head><body>"
    val end = "</body></html>"

    return "$start$body$end"
}


fun getEmptyNews() = News("", "", "", "", "", "")

fun parseNewsAnimeList(httpResult: String) =
    Jsoup.parse(httpResult)
        .select("div[class=container] div[class=content] ul[class=ui-repeater] li").map {


            val imgUrl = it.select("div[class=imgbox]>a>img").attr("src")

            val textElement = it.select("p[class=textbox]>a")
            val title = textElement.text()
            val url = textElement.attr("href")


            val labelElement = it.select("p[class=labelbox]")
            val author = labelElement.select("span[class=author]").text()
            val time = labelElement.select("span[class=time]").attr("data-time")
            val tags = formatTags(labelElement.select("span[class=tag]").text())


            News(title, url, imgUrl, author, time, tags)

        }

fun getRandomAvatar() = listOf(
    R.mipmap.avator_01,
    R.mipmap.avator_02,
    R.mipmap.avator_03,
    R.mipmap.avator_04,
    R.mipmap.avator_05,
    R.mipmap.avator_06,
    R.mipmap.avator_07,
    R.mipmap.avator_08,
    R.mipmap.avator_09,
    R.mipmap.avator_10,
    R.mipmap.avator_11
).random()


fun parseAmlyu(result: String): List<Photo> {
    val elements = Jsoup.parse(result)
        .select("div[class=excerpts]>article[class=excerpt excerpt-c5 excerpt-hoverplugin]>a>img")
    return elements.map { e ->
        Photo(e.attr("data-src"))
    }
}


