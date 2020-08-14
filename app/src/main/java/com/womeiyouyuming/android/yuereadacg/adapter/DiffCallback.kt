package com.womeiyouyuming.android.yuereadacg.adapter

import androidx.recyclerview.widget.DiffUtil
import com.womeiyouyuming.android.yuereadacg.model.*

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

object AnimeDiffCallback : DiffUtil.ItemCallback<Anime>() {
    override fun areItemsTheSame(oldItem: Anime, newItem: Anime) = oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: Anime, newItem: Anime) = oldItem == newItem
}

object PlayDiffCallback : DiffUtil.ItemCallback<Play>() {
    override fun areItemsTheSame(oldItem: Play, newItem: Play) = oldItem.url == newItem.url
    override fun areContentsTheSame(oldItem: Play, newItem: Play) = oldItem == newItem
}

object PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.url == newItem.url
    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
}