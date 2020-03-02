package com.womeiyouyuming.android.yuereadacg.fragment.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.adapter.BannerAdapter
import com.womeiyouyuming.android.yuereadacg.adapter.NewsHomeAdapter
import com.womeiyouyuming.android.yuereadacg.network.NetworkState
import com.womeiyouyuming.android.yuereadacg.util.getEmptyNews
import com.womeiyouyuming.android.yuereadacg.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.error.*
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


        initSwipeRefresh()
        initNewsHomeRecyclerView()


    }

    private fun initSwipeRefresh() {

        val color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
        swipeRefresh.setColorSchemeColors(color)


        swipeRefresh.setOnRefreshListener {
            newsViewModel.refreshNewsHome()
        }

        refreshButton.setOnClickListener {
            newsViewModel.refreshNewsHome()
        }




        newsViewModel.networkStateLiveData.observe(viewLifecycleOwner) {


            when(it) {

                NetworkState.FIRST_LOADING, NetworkState.LOADING -> {
                    refreshButton.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    swipeRefresh.isRefreshing = true
                }
                NetworkState.FAILED -> {
                    swipeRefresh.isRefreshing = false

                    refreshButton.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE

                    newsViewModel.newsListLiveDate.value?.let {
                        Toast.makeText(requireContext(), R.string.text_error, Toast.LENGTH_SHORT).show()
                        errorLayout.visibility = View.GONE
                    } ?: let {
                        errorLayout.visibility = View.VISIBLE
                    }

                }
                NetworkState.SUCCESS -> {
                    swipeRefresh.isRefreshing = false

                    refreshButton.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE


                    errorLayout.visibility = View.GONE
                }

            }

        }

    }


    private fun initNewsHomeRecyclerView() {

        val bannerAdapter = BannerAdapter {
            val bundle = bundleOf(Pair("url", it))
            findNavController().navigate(R.id.action_nav_news_to_nav_news_content, bundle)
        }


        val newsHomeAdapter = NewsHomeAdapter(bannerAdapter) { url, author ->
            val bundle = bundleOf(Pair("url", url), Pair("author", author))
            findNavController().navigate(R.id.action_nav_news_to_nav_news_content, bundle)
        }

        with(newsHomeRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsHomeAdapter

        }

        newsViewModel.newsSwipeListLiveData.observe(viewLifecycleOwner) {
                bannerAdapter.submitList(it)
        }


        newsViewModel.newsListLiveDate.observe(viewLifecycleOwner) {

            //给list首加一个空元素，对齐rv
            val list = listOf(getEmptyNews()) + it
            newsHomeAdapter.submitList(list)
        }


    }

}
