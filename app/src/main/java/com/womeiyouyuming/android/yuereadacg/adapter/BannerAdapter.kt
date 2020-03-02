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
 *
 * 资讯——首页 的 banner adapter
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
                val url = bannerBinding.newsSwipe?.url ?: ""
                itemClick(url)
            }
        }
    }

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        holder.bind(getItem(position))
    }




    class BannerHolder(private val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(newsSwipe: NewsSwipe) {
            binding.newsSwipe = newsSwipe
        }

    }


}