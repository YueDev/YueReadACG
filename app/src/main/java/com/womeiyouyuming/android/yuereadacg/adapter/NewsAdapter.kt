package com.womeiyouyuming.android.yuereadacg.adapter

import com.womeiyouyuming.android.yuereadacg.model.News

/**
 * Created by Yue on 2020/1/13.
 */
class NewsAdapter: BaseNewsAdapter() {

    override fun getItemViewType(position: Int) = when(getItem(position).tags) {
            "动画", "漫画" -> typeHot
            else -> typeCommon
        }

}