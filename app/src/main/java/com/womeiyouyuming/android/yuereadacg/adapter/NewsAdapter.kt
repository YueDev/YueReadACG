package com.womeiyouyuming.android.yuereadacg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.databinding.ItemBannerBinding
import com.womeiyouyuming.android.yuereadacg.databinding.ItemNewsCommonBinding
import com.womeiyouyuming.android.yuereadacg.databinding.ItemNewsHotBinding
import com.womeiyouyuming.android.yuereadacg.model.News

/**
 * Created by Yue on 2020/1/10.
 */
class NewsAdapter : ListAdapter<News, NewsAdapter.NewsHolder>(NewsDiffCallback) {

    private val typeCommon = 0
    private val typeHot = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            typeHot -> NewsHolder(DataBindingUtil.inflate<ItemNewsHotBinding>(inflater, R.layout.item_news_hot, parent, false))
            else -> NewsHolder(DataBindingUtil.inflate<ItemNewsCommonBinding>(inflater, R.layout.item_news_common, parent, false))
        }
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        when (getItemViewType(position)) {
            typeHot -> holder.bindHot(getItem(position))
            typeCommon -> holder.bindCommon(getItem(position))
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position).tags) {
        "动画", "漫画" -> typeHot
        else -> typeCommon

    }

    object NewsDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News) = oldItem == newItem

        override fun areContentsTheSame(oldItem: News, newItem: News) = oldItem == newItem
    }

    class NewsHolder : RecyclerView.ViewHolder {


        private lateinit var itemNewsCommonBinding: ItemNewsCommonBinding
        private lateinit var itemNewsHotBinding: ItemNewsHotBinding

        constructor(itemNewsCommonBinding: ItemNewsCommonBinding) : super(itemNewsCommonBinding.root) {
            this.itemNewsCommonBinding = itemNewsCommonBinding
        }

        constructor(itemNewsHotBinding: ItemNewsHotBinding) : super(itemNewsHotBinding.root) {
            this.itemNewsHotBinding = itemNewsHotBinding
        }

        fun bindCommon(news: News) {
            itemNewsCommonBinding.news = news
        }

        fun bindHot(news: News) {
            itemNewsHotBinding.news = news
        }

    }
}