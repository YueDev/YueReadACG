package com.womeiyouyuming.android.yuereadacg.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Yue on 2020/8/3.
 */

data class Anime(
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("playlist")
    val playlist: List<Play>
) : Serializable

data class Play(
    @SerializedName("url")
    val url: String,
    @SerializedName("name")
    val name: String
) : Serializable