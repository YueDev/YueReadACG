package com.womeiyouyuming.android.yuereadacg.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.databinding.ItemNewsCommonBinding
import com.womeiyouyuming.android.yuereadacg.databinding.ItemNewsHotBinding
import com.womeiyouyuming.android.yuereadacg.model.News

/**
 * Created by Yue on 2020/1/10.
 * 所有资讯列表Adapter的基类
 * 只需要复写getItemViewType()来区分普通资讯和热点资讯
 *
 */


abstract class BaseNewsAdapter(private val itemClick: (url: String) -> Unit) :
    PagedListAdapter<News, BaseNewsAdapter.NewsHolder>(NewsDiffCallback) {

    protected val typeCommon = 0
    protected val typeHot = 1

    abstract override fun getItemViewType(position: Int): Int


    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            typeHot -> {
                val binding = DataBindingUtil.inflate<ItemNewsHotBinding>(
                    inflater,
                    R.layout.item_news_hot,
                    parent,
                    false
                )
                NewsHolder(binding).apply {
                    itemView.setOnClickListener {
                        val url = binding.news?.url ?: throw NullPointerException("url is null")
                        itemClick(url)
                    }
                }
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemNewsCommonBinding>(
                    inflater,
                    R.layout.item_news_common,
                    parent,
                    false
                )
                NewsHolder(binding).apply {
                    itemView.setOnClickListener {
                        val url = binding.news?.url ?: throw NullPointerException("url is null")
                        itemClick(url)
                    }
                }
            }
        }
    }


    final override fun onBindViewHolder(holder: NewsHolder, position: Int) {

        when (getItemViewType(position)) {
            typeHot -> holder.bindHot(getItem(position))
            typeCommon -> holder.bindCommon(getItem(position))
        }
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

        fun bindCommon(news: News?) {

            news?.let {
                itemNewsCommonBinding.news = news
            }

        }


        fun bindHot(news: News?) {

            news?.let {
                itemNewsHotBinding.news = news
            }
        }

    }
}


