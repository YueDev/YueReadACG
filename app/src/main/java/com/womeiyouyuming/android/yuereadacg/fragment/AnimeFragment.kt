package com.womeiyouyuming.android.yuereadacg.fragment

import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.marginTop
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.adapter.AnimeAdapter
import com.womeiyouyuming.android.yuereadacg.network.NetworkState
import com.womeiyouyuming.android.yuereadacg.viewmodel.AnimeViewModel
import kotlinx.android.synthetic.main.fragment_anime.*
import kotlinx.android.synthetic.main.fragment_anime.swipeRefresh
import kotlinx.android.synthetic.main.toolbar.*

/**
 * A simple [Fragment] subclass.
 */
class AnimeFragment : Fragment() {

    private val animeViewModel by activityViewModels<AnimeViewModel>()


    //rv的列数，根据屏幕的宽高来决定，默认竖屏是2 横屏是4
    private val spanCountPort = 2
    private val spanCountLand = 3


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolBar.title = resources.getString(R.string.lab_anime)

        toolBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        initSwipeRefresh()
        initRecyclerView()


        animeViewModel.animeListLiveData.observe(viewLifecycleOwner) {

        }

    }


    override fun onDestroyView() {


        //保存rv位置。这里需要判断是其他页面才存储。当前页面不需要存储，系统恢复即可
        // 这样可以防止连续点击anime 按钮，不停的在当前页面新建AnimeFragment
        // 这时触发顺序：新AnimeFragment onCreateView -----> 老AnimeFragment的 onDestroyView
        // 这样我们是不需要存储的，因此加了个当前页面ID判断，只有跳转到新的页面才保存
        (animeRecyclerView.layoutManager as GridLayoutManager).let {

            if (findNavController().currentDestination?.id == R.id.nav_anime) return@let
            val topView = it.getChildAt(0) ?: return@let
            animeViewModel.rvPosition = it.getPosition(topView)
            animeViewModel.rvOffset = topView.top - topView.marginTop

        }

        super.onDestroyView()

    }


    private fun initRecyclerView() {

        val point = Point()
        requireActivity().windowManager.defaultDisplay.getRealSize(point)

//            //API30以上用这个
//            requireContext().display?.getRealSize(point)

        val spanCount = if (point.x > point.y) spanCountLand else spanCountPort


        val animeAdapter = AnimeAdapter(retry = { animeViewModel.retry() }, itemClick = {
//            val bundle = bundleOf("anime" to it)
//            findNavController().navigate(R.id.action_nav_anime_to_playActivity, bundle)
        })


        animeRecyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount).also {
            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) =
                    if (animeAdapter.getItemViewType(position) == R.layout.item_news_network_state) spanCount else 1
            }
        }

        animeRecyclerView.adapter = animeAdapter


        animeViewModel.animeListLiveData.observe(viewLifecycleOwner, Observer {

            animeAdapter.submitList(it)

            //恢复rv之前的位置
            if (animeViewModel.rvPosition >= 0) {
                (animeRecyclerView.layoutManager as GridLayoutManager).scrollToPositionWithOffset(
                    animeViewModel.rvPosition,
                    animeViewModel.rvOffset
                )
                animeViewModel.rvPosition = -1
                animeViewModel.rvOffset = -1
            }

        })

        animeViewModel.networkStateLiveData.observe(viewLifecycleOwner, Observer {
            swipeRefresh.isRefreshing = it == NetworkState.FIRST_LOADING
            animeAdapter.setNetworkState(it)

        })
    }

    private fun initSwipeRefresh() {

        val color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
        swipeRefresh.setColorSchemeColors(color)
        swipeRefresh.setOnRefreshListener { animeViewModel.refresh() }
    }


}

