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
import com.womeiyouyuming.android.yuereadacg.network.NetworkState

/**
 * Created by Yue on 2020/1/10.
 * 所有资讯列表Adapter的基类
 * 只需要复写getItemViewType()来区分普通资讯和热点资讯
 *
 */


abstract class BaseNewsAdapter(private val itemClick: (url: String) -> Unit) :
    PagedListAdapter<News, RecyclerView.ViewHolder>(NewsDiffCallback) {


    private var networkState: NetworkState? = null

    private fun hasExtraRow() =
        (networkState == NetworkState.FAILED) && (networkState == NetworkState.LOADING)

    abstract fun isHotTag(): Boolean


    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_news_hot -> NewsHotHolder(
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.item_news_hot,
                    parent,
                    false
                ), itemClick
            )
            R.layout.item_news_common -> NewsCommonHolder(
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.item_news_hot,
                    parent,
                    false
                ), itemClick
            )
            else -> throw IllegalArgumentException("No such viewType : $viewType")
        }
    }


    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


    }

    final override fun getItemCount() = super.getItemCount() + if (hasExtraRow()) 1 else 0


    final override fun getItemViewType(position: Int) =
        when {
            hasExtraRow() && position == itemCount - 1 -> R.layout.error
            isHotTag() -> R.layout.item_news_hot
            else -> R.layout.item_news_common
        }


    class NewsHotHolder(private val binding: ItemNewsHotBinding, itemClick: (url: String) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemClick(binding.news?.url ?: throw NullPointerException("url is null"))
            }
        }

        fun bind(news: News?) {

            news?.let {
                binding.news = news
            }

        }
    }

    class NewsCommonHolder(
        private val binding: ItemNewsCommonBinding,
        itemClick: (url: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemClick(binding.news?.url ?: throw NullPointerException("url is null"))
        }

        fun bind(news: News?) {

            news?.let {
                binding.news = news
            }

        }

    }

}

