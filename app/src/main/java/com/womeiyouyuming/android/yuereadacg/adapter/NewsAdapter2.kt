package com.womeiyouyuming.android.yuereadacg.adapter

import android.view.View
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.databinding.ItemNewsCommonBinding
import com.womeiyouyuming.android.yuereadacg.databinding.ItemNewsHotBinding
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.view.ZoomPageTransformer

/**
 * Created by Yue on 2020/1/15.
 */
class NewsAdapter2(private val bannerAdapter: BannerAdapter) {


    class NewsHolder : RecyclerView.ViewHolder {

        private lateinit var bannerViewPager: ViewPager2
        private lateinit var itemNewsCommonBinding: ItemNewsCommonBinding
        private lateinit var itemNewsHotBinding: ItemNewsHotBinding


        constructor(itemView: View) : super(itemView) {
            bannerViewPager = itemView.findViewById(R.id.bannerViewPager)

            initBanner()

        }


        constructor(itemNewsCommonBinding: ItemNewsCommonBinding) : super(itemNewsCommonBinding.root) {
            this.itemNewsCommonBinding = itemNewsCommonBinding
        }

        constructor(itemNewsHotBinding: ItemNewsHotBinding) : super(itemNewsHotBinding.root) {
            this.itemNewsHotBinding = itemNewsHotBinding
        }

        fun bindBanner() {

        }

        fun bindCommon(news: News) {
            itemNewsCommonBinding.news = news
        }

        fun bindHot(news: News) {
            itemNewsHotBinding.news = news
        }


        private fun initBanner() {

            val pageTransformer = CompositePageTransformer().apply {
                //设置页面的间距和缩放效果
                addTransformer(MarginPageTransformer(10))
                addTransformer(ZoomPageTransformer())
            }
//

//
//        with(bannerViewPager) {
//            setPageTransformer(pageTransformer)
//            adapter = bannerAdapter
//
//
//            //获取viewpager内部recyclerview，设置padding用来显示多个页面
//            (getChildAt(0) as RecyclerView).also {
//                it.setPadding(50, 0, 50, 0)
//                it.clipToPadding = false
//            }
//        }
//
//
//
//        newsViewModel.newsSwipeListLiveData.observe(viewLifecycleOwner) {
//            bannerAdapter.submitList(it)
//        }
        }


    }
}