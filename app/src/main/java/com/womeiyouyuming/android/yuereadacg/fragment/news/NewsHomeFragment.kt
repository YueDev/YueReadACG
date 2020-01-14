package com.womeiyouyuming.android.yuereadacg.fragment.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2

import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.adapter.BannerAdapter
import com.womeiyouyuming.android.yuereadacg.adapter.NewsAdapter
import com.womeiyouyuming.android.yuereadacg.view.ZoomPageTransformer
import com.womeiyouyuming.android.yuereadacg.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news_home.*

/**
 * A simple [Fragment] subclass.
 */
class NewsHomeFragment : Fragment() {

    private val newsViewModel by activityViewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_news_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initBanner()

        initNewsRecyclerView()

    }





    private fun initBanner() {
        val pageTransformer = CompositePageTransformer().apply {
            //设置页面的间距和缩放效果
            addTransformer(MarginPageTransformer(10))
            addTransformer(ZoomPageTransformer())
        }

        val bannerAdapter = BannerAdapter {
            val bundle = bundleOf(Pair("url", it))
            findNavController().navigate(R.id.action_nav_news_to_nav_news_content, bundle)
        }

        with(bannerViewPager) {
            setPageTransformer(pageTransformer)
            adapter = bannerAdapter


            //获取viewpager内部recyclerview，设置padding用来显示多个页面
            (getChildAt(0) as RecyclerView).also {
                it.setPadding(50, 0, 50, 0)
                it.clipToPadding = false
            }
        }



        newsViewModel.newsSwipeListLiveData.observe(viewLifecycleOwner) {
            bannerAdapter.submitList(it)
        }
    }


    private fun initNewsRecyclerView() {

        val newsAdapter = NewsAdapter()

        with(newsRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }

        newsViewModel.newsListLiveDate.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }

    }

}
