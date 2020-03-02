package com.womeiyouyuming.android.yuereadacg.fragment.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager

import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.adapter.NewsAdapter
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.network.NetworkState
import com.womeiyouyuming.android.yuereadacg.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news_anime.*
import kotlinx.android.synthetic.main.fragment_news_anime.swipeRefresh

/**
 *
 *  资讯下每一个News分类的基类Fragment(除去首页)
 *
 *  下边有实现类
 *
 */
abstract class BaseNewsFragment : Fragment() {

    protected val newsViewModel by activityViewModels<NewsViewModel>()

    abstract val hotTags: List<String>

    abstract fun getNewsLiveData(): LiveData<PagedList<News>>
    abstract fun getNewsNetworkStateLiveData(): LiveData<NetworkState>

    abstract fun refresh()
    abstract fun retry()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_anime, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        initSwipeRefresh()
        initNewsRecyclerView()


    }

    private fun initSwipeRefresh() {

        val color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
        swipeRefresh.setColorSchemeColors(color)

        swipeRefresh.setOnRefreshListener {
            refresh()
        }

    }

    private fun initNewsRecyclerView() {


        val adapter = NewsAdapter(hotTags,
            itemClick = { url, author ->
                val bundle = bundleOf(Pair("url", url), Pair("author", author))
                findNavController().navigate(R.id.action_nav_news_to_nav_news_content, bundle)
            },
            retry = { retry() })

        newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newsRecyclerView.adapter = adapter

        getNewsLiveData().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        getNewsNetworkStateLiveData().observe(viewLifecycleOwner, Observer {
            swipeRefresh.isRefreshing = it == NetworkState.FIRST_LOADING
            adapter.setNetworkState(it)
        })

    }

}


class NewsAnimeFragment : BaseNewsFragment() {

    override val hotTags: List<String> = listOf("动画", "漫画", "剧场版")

    override fun getNewsLiveData() = newsViewModel.newsAnimeListLiveData

    override fun getNewsNetworkStateLiveData() = newsViewModel.newsAnimeNetworkStateLiveData

    override fun refresh() {
        newsViewModel.refreshNewsAnime()
    }

    override fun retry() {
        newsViewModel.retryNewsAnime()
    }
}

class NewsExpoFragment : BaseNewsFragment() {

    override val hotTags: List<String> = listOf("展会")
    override fun getNewsLiveData() = newsViewModel.newsExpoListLiveData

    override fun getNewsNetworkStateLiveData() = newsViewModel.newsExpoNetworkStateLiveData

    override fun refresh() {
        newsViewModel.refreshNewsExpo()
    }

    override fun retry() {
        newsViewModel.retryNewsExpo()
    }

}

class NewsBeautyFragment : BaseNewsFragment() {

    override val hotTags: List<String> = listOf("福利")
    override fun getNewsLiveData() = newsViewModel.newsBeautyListLiveData

    override fun getNewsNetworkStateLiveData() = newsViewModel.newsBeautyNetworkStateLiveData

    override fun refresh() {
        newsViewModel.refreshNewsBeauty()
    }

    override fun retry() {
        newsViewModel.retryNewsBeauty()
    }

}class NewsFigmaFragment : BaseNewsFragment() {

    override val hotTags: List<String> = listOf("手办")
    override fun getNewsLiveData() = newsViewModel.newsFigmaListLiveData

    override fun getNewsNetworkStateLiveData() = newsViewModel.newsFigmaNetworkStateLiveData

    override fun refresh() {
        newsViewModel.refreshNewsFigma()
    }

    override fun retry() {
        newsViewModel.retryNewsFigma()
    }

}class NewsSpecialFragment : BaseNewsFragment() {

    override val hotTags: List<String> = listOf("宅科学", "在前线")
    override fun getNewsLiveData() = newsViewModel.newsSpecialListLiveData

    override fun getNewsNetworkStateLiveData() = newsViewModel.newsSpecialNetworkStateLiveData

    override fun refresh() {
        newsViewModel.refreshNewsSpecial()
    }

    override fun retry() {
        newsViewModel.retryNewsSpecial()
    }

}class NewsBaguaFragment : BaseNewsFragment() {

    override val hotTags: List<String> = listOf("八卦")
    override fun getNewsLiveData() = newsViewModel.newsBaguaListLiveData

    override fun getNewsNetworkStateLiveData() = newsViewModel.newsBaguaNetworkStateLiveData

    override fun refresh() {
        newsViewModel.refreshNewsBagua()
    }

    override fun retry() {
        newsViewModel.retryNewsBagua()
    }

}




class NewsGameFragment : BaseNewsFragment() {
    override val hotTags: List<String> = listOf("手游")

    override fun getNewsLiveData() = newsViewModel.newsGameLiveData

    override fun getNewsNetworkStateLiveData() = newsViewModel.newsGameNetworkStateLiveData

    override fun refresh() {
        newsViewModel.refreshNewsGame()
    }

    override fun retry() {
        newsViewModel.retryNewsGame()
    }
}

