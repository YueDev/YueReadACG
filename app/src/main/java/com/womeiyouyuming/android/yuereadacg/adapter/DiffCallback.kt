package com.womeiyouyuming.android.yuereadacg.adapter

import androidx.recyclerview.widget.DiffUtil
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.model.NewsSwipe

/**
 * Created by Yue on 2020/1/27.
 *
 */

object NewsDiffCallback : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News) = oldItem == newItem

    override fun areContentsTheSame(oldItem: News, newItem: News) = oldItem == newItem
}

object BannerDiffCallback : DiffUtil.ItemCallback<NewsSwipe>() {
    override fun areItemsTheSame(oldItem: NewsSwipe, newItem: NewsSwipe) = oldItem == newItem

    override fun areContentsTheSame(oldItem: NewsSwipe, newItem: NewsSwipe) = oldItem == newItem
}