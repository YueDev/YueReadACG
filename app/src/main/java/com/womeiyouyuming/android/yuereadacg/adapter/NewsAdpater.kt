package com.womeiyouyuming.android.yuereadacg.adapter

/**
 * Created by Yue on 2020/1/27.
 */
class NewsAnimeAdapter(itemClick: (url: String) -> Unit) : BaseNewsAdapter(itemClick) {



    override fun getItemViewType(position: Int) = when (getItem(position)?.tags) {
        "动画", "漫画", "剧场版" -> typeHot
        else -> typeCommon
    }


}