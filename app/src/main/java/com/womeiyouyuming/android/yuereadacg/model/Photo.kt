package com.womeiyouyuming.android.yuereadacg.model

/**
 * Created by Yue on 2020/8/14.
 */

data class Photo(val url:String, val thumbUrl:String, val title:String) {
    constructor(url: String, thumbUrl:String) : this(url, thumbUrl, url.substringAfterLast("/"))
    constructor(url:String) : this(url, url)
}
