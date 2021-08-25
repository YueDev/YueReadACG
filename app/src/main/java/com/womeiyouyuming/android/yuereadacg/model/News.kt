package com.womeiyouyuming.android.yuereadacg.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Yue on 2020/1/9.
 */

//资讯-首页 recyclerview的model类
data class News(
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("figure")
    val imgUrl: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("tags")
    val tags: String
)