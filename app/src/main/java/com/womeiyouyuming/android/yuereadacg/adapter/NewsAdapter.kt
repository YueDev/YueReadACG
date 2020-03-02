package com.womeiyouyuming.android.yuereadacg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.databinding.ItemNewsCommonBinding
import com.womeiyouyuming.android.yuereadacg.databinding.ItemNewsHotBinding
import com.womeiyouyuming.android.yuereadacg.databinding.ItemNewsNetworkStateBinding
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.network.NetworkState

/**
 * Created by Yue on 2020/1/10.
 * 所有资讯列表Adapter的基类
 * 子类只需要实现isHotTag()来区分普通资讯和热点资讯
 *
 *
 * 不抽象了，直接把hotTags拿来做参数
 */


class NewsAdapter(
    private val hotTags: List<String>,
    private val itemClick: (url: String, author: String?) -> Unit,
    private val retry: () -> Unit
) :
    PagedListAdapter<News, RecyclerView.ViewHolder>(NewsDiffCallback) {


    private var networkState: NetworkState? = null


    private fun hasExtraRow() =
        (networkState == NetworkState.FAILED) || (networkState == NetworkState.LOADING)


    fun setNetworkState(networkState: NetworkState) {

        val hadExtraRow = hasExtraRow()
        this.networkState = networkState
        val hasExtraRow = hasExtraRow()

        when {
            hadExtraRow && !hasExtraRow -> notifyItemRemoved(super.getItemCount())
            !hadExtraRow && hasExtraRow -> notifyItemInserted(super.getItemCount())
            hasExtraRow -> notifyItemChanged(super.getItemCount())
        }


    }


    override fun onCreateViewHolder(
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
                    R.layout.item_news_common,
                    parent,
                    false
                ), itemClick
            )

            R.layout.item_news_network_state -> NewsNetworkStateHolder(
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.item_news_network_state,
                    parent,
                    false
                ), retry
            )
            else -> throw IllegalArgumentException("No such viewType : $viewType")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            R.layout.item_news_hot -> (holder as NewsHotHolder).bind(getItem(position))
            R.layout.item_news_common -> (holder as NewsCommonHolder).bind(getItem(position))
            R.layout.item_news_network_state -> (holder as NewsNetworkStateHolder).bind(networkState)
        }

    }

    override fun getItemCount() = super.getItemCount() + if (hasExtraRow()) 1 else 0


    override fun getItemViewType(position: Int) =
        when {
            hasExtraRow() && position == itemCount - 1 -> R.layout.item_news_network_state
            getItem(position)?.tags in hotTags -> R.layout.item_news_hot
            else -> R.layout.item_news_common
        }


    class NewsHotHolder(private val binding: ItemNewsHotBinding, itemClick: (url: String, author: String?) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemClick(binding.news?.url ?: throw NullPointerException("url is null"), binding.news?.author)
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
        itemClick: (url: String, author: String?) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemClick(binding.news?.url ?: throw NullPointerException("url is null"), binding.news?.author)
            }
        }

        fun bind(news: News?) {

            news?.let {
                binding.news = news
            }

        }

    }

    class NewsNetworkStateHolder(
        private val binding: ItemNewsNetworkStateBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener {
                retry()
            }
        }

        fun bind(networkState: NetworkState?) {
            networkState?.let {
                binding.networkState = it
            }

        }

    }

}


