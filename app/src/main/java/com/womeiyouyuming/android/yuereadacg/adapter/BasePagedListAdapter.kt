package com.womeiyouyuming.android.yuereadacg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.databinding.ItemNewsNetworkStateBinding
import com.womeiyouyuming.android.yuereadacg.network.NetworkState

/**
 * Created by Yue on 2020/8/5.
 * 所有带有错误处理的PagedListAdapter的基类
 *
 *
 *
 */


abstract class BasePagedListAdapter<T:Any>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {


    //item的layout布局
    abstract val itemLayoutResId: Int

    //network error 的 retry,一般是viewmodel里的重新请求方法
    abstract val retry: () -> Unit


    //创建ViewHolder
    abstract fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    //bindViewHolder
    abstract fun bindViewHolder(holder: RecyclerView.ViewHolder, item: T?)

    private var networkState: NetworkState? = null


    private fun hasExtraRow() =
        (networkState == NetworkState.FAILED) || (networkState == NetworkState.LOADING)


    //设置网络请求错误的监听，加载错误布局
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

            //这里需要写正常的ViewHolder
            itemLayoutResId -> createViewHolder(parent)

            //网络错误的ViewHolder
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
            itemLayoutResId -> bindViewHolder(holder, getItem(position))
            R.layout.item_news_network_state -> (holder as NewsNetworkStateHolder).bind(networkState)
        }

    }

    override fun getItemCount() = super.getItemCount() + if (hasExtraRow()) 1 else 0


    override fun getItemViewType(position: Int) =
        when {
            hasExtraRow() && (position == itemCount - 1) -> R.layout.item_news_network_state
            else -> itemLayoutResId
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


