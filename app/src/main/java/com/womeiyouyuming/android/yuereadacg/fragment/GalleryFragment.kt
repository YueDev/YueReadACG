package com.womeiyouyuming.android.yuereadacg.fragment

import android.graphics.Point
import android.os.Bundle
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.adapter.GalleryAdapter
import com.womeiyouyuming.android.yuereadacg.network.NetworkState
import com.womeiyouyuming.android.yuereadacg.viewmodel.GalleryViewModel
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.swipeRefresh

/**
 * A simple [Fragment] subclass.
 *
 * copy from AnimeFragment
 * 懒的抽取基类了
 */
class GalleryFragment : Fragment() {

    private val viewModel by activityViewModels<GalleryViewModel>()

    //rv的列数，根据屏幕的宽高来决定，默认竖屏是2 横屏是4
    private val spanCountPort = 2
    private val spanCountLand = 4


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initSwipeRefresh()
        initRecyclerView()


    }

    override fun onDestroyView() {

        (galleryRecyclerView.layoutManager as GridLayoutManager).let {

            if (findNavController().currentDestination?.id == R.id.nav_gallery) return@let
            val topView = it.getChildAt(0) ?: return@let
            viewModel.rvPosition = it.getPosition(topView)
            viewModel.rvOffset = topView.top - topView.marginTop

        }

        super.onDestroyView()

    }

    private fun initRecyclerView() {

        val point = Point()
        requireActivity().windowManager.defaultDisplay.getRealSize(point)

//            //API30以上用这个
//            requireContext().display?.getRealSize(point)

        val spanCount = if (point.x > point.y) spanCountLand else spanCountPort


        val adapter = GalleryAdapter(retry = { viewModel.retry() }, itemClick = {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })


        galleryRecyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount).also {
            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) =
                    if (adapter.getItemViewType(position) == R.layout.item_news_network_state) spanCount else 1
            }
        }


        galleryRecyclerView.adapter = adapter


        viewModel.photoListLiveData.observe(viewLifecycleOwner, Observer {

            adapter.submitList(it)

            //恢复rv之前的位置
            if (viewModel.rvPosition >= 0) {
                (galleryRecyclerView.layoutManager as GridLayoutManager).scrollToPositionWithOffset(
                    viewModel.rvPosition,
                    viewModel.rvOffset
                )
                viewModel.rvPosition = -1
                viewModel.rvOffset = -1
            }

        })

        viewModel.networkStateLiveData.observe(viewLifecycleOwner, Observer {
            swipeRefresh.isRefreshing = it == NetworkState.FIRST_LOADING
            adapter.setNetworkState(it)

        })

    }

    private fun initSwipeRefresh() {

        val color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
        swipeRefresh.setColorSchemeColors(color)
        swipeRefresh.setOnRefreshListener { viewModel.refresh() }

    }


}
