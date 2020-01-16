package com.womeiyouyuming.android.yuereadacg.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.databinding.ItemNewsCommonBinding
import com.womeiyouyuming.android.yuereadacg.databinding.ItemNewsHotBinding
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.util.getEmptyNews
import com.womeiyouyuming.android.yuereadacg.view.ZoomPageTransformer

/**
 * Created by Yue on 2020/1/15.
 *
 * 资讯——首页的adapter
 * head是viewpager
 *
 * 此adapter不负责viewpager的数据，viewpager的数据交给它自己的bannerAdapter来处理
 *
 * 为了使数据对齐，需要在源数据list上加加上一个首元素，可以为无意义的数据，
 * 如果不对齐，复写getItemCount会使列表滑动到最后一行。没查到原因
 *
 */
class NewsHomeAdapter(
    private val bannerAdapter: BannerAdapter,
    private val itemClick: (url: String) -> Unit
) :
    ListAdapter<News, NewsHomeAdapter.NewsHomeHolder>(NewsDiffCallback) {

    private val typeHead = 1357

    private val typeCommon = 0
    private val typeHot = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHomeHolder {

        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {

            typeHead -> {
                val itemView = inflater.inflate(R.layout.item_news_home_head, parent, false)
                return NewsHomeHolder(itemView, bannerAdapter)
            }


            typeHot -> {
                val binding = DataBindingUtil.inflate<ItemNewsHotBinding>(
                    inflater,
                    R.layout.item_news_hot,
                    parent,
                    false
                )

                val holder = NewsHomeHolder(binding)
                holder.itemView.setOnClickListener {
                    val url = binding.news?.url ?: throw NullPointerException("url is null")
                    itemClick(url)
                }
                return holder
            }


            else -> {
                val binding = DataBindingUtil.inflate<ItemNewsCommonBinding>(
                    inflater,
                    R.layout.item_news_common,
                    parent,
                    false
                )
                val holder = NewsHomeHolder(binding)
                holder.itemView.setOnClickListener {
                    val url = binding.news?.url ?: throw NullPointerException("url is null")
                    itemClick(url)
                }
                return holder
            }
        }
    }


    override fun onBindViewHolder(holder: NewsHomeHolder, position: Int) {
        when (getItemViewType(position)) {
            //负责viewpager的数据，viewpager的数据交给它自己的bannerAdapter来处理
            typeHead -> return
            typeHot -> holder.bindHot(getItem(position ))
            typeCommon -> holder.bindCommon(getItem(position))
        }
    }




    override fun getItemViewType(position: Int): Int {

        if (position == 0) return typeHead

        return when (getItem(position).tags) {
            "动画", "漫画" -> typeHot
            else -> typeCommon
        }
    }



    class NewsHomeHolder : RecyclerView.ViewHolder {

        private lateinit var bannerViewPager: ViewPager2
        private lateinit var itemNewsCommonBinding: ItemNewsCommonBinding
        private lateinit var itemNewsHotBinding: ItemNewsHotBinding


        constructor(itemView: View, adapter: BannerAdapter) : super(itemView) {

            bannerViewPager = itemView.findViewById(R.id.bannerViewPager)
            initBanner(adapter)

        }


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


        private fun initBanner(adapter: BannerAdapter) {

            val pageTransformer = CompositePageTransformer().apply {
                //设置页面的间距和缩放效果
                addTransformer(MarginPageTransformer(10))
                addTransformer(ZoomPageTransformer())
            }

            bannerViewPager.setPageTransformer(pageTransformer)
            bannerViewPager.adapter = adapter


            //获取viewpager内部recyclerview，设置padding用来显示多个页面
            (bannerViewPager.getChildAt(0) as RecyclerView).apply {
                setPadding(50, 0, 50, 0)
                clipToPadding = false
            }

        }


    }
}