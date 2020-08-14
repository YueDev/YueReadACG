package com.womeiyouyuming.android.yuereadacg.model

import java.io.Serializable

/**
 * Created by Yue on 2020/8/3.
 */

data class Anime(
    val title: String,
    val url: String,
    val img: String,
    val time: String,
    val author: String,
    val content: String,
    val playlist: List<Play>
) : Serializable

data class Play(val url: String, val name: String) : Serializable