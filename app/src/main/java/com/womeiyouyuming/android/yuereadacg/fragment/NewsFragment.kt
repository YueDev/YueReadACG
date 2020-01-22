package com.womeiyouyuming.android.yuereadacg.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.adapter.NewsPagerAdapter
import com.womeiyouyuming.android.yuereadacg.util.NEWS_PAGE_LIST
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        viewPager.adapter = NewsPagerAdapter(this)

        var i = 1

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = NEWS_PAGE_LIST[position]
        }.attach()



    }


}
