package com.womeiyouyuming.android.yuereadacg.adapter

import androidx.recyclerview.widget.DiffUtil
import com.womeiyouyuming.android.yuereadacg.model.News

/**
 * Created by Yue on 2020/2/4.
 */
object NewsDiffCallback : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News) = oldItem == newItem

    override fun areContentsTheSame(oldItem: News, newItem: News) = oldItem == newItem
}
