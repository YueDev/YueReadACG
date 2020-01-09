package com.womeiyouyuming.android.yuereadacg.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Yue on 2020/1/9.
 */

//资讯-首页 recyclerview的model类
data class News(
    val title: String,
    val url: String,
    @SerializedName("figure")
    val imgUrl: String,
    val author: String,
    val time: String,
    val tags: String
)