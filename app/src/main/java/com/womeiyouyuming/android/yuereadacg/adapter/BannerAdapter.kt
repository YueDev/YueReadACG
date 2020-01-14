package com.womeiyouyuming.android.yuereadacg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.databinding.ItemBannerBinding
import com.womeiyouyuming.android.yuereadacg.model.NewsSwipe

/**
 * Created by Yue on 2020/1/10.
 */
class BannerAdapter(private val itemClick: (url: String) -> Unit) : ListAdapter<NewsSwipe, BannerAdapter.BannerHolder>(BannerDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder {
        val bannerBinding = DataBindingUtil.inflate<ItemBannerBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_banner,
            parent,
            false
        )

        return BannerHolder(bannerBinding).apply {
            itemView.setOnClickListener {
                bannerBinding.newsSwipe?.let {
                    itemClick(it.url)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object BannerDiffCallback : DiffUtil.ItemCallback<NewsSwipe>() {
        override fun areItemsTheSame(oldItem: NewsSwipe, newItem: NewsSwipe) = oldItem == newItem

        override fun areContentsTheSame(oldItem: NewsSwipe, newItem: NewsSwipe) = oldItem == newItem
    }


    class BannerHolder(private val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(newsSwipe: NewsSwipe) {
            binding.newsSwipe = newsSwipe
        }

    }

}