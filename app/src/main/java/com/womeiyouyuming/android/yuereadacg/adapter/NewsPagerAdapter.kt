package com.womeiyouyuming.android.yuereadacg.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.womeiyouyuming.android.yuereadacg.fragment.news.NewsHomeFragment

/**
 * Created by Yue on 2020/1/9.
 */
class NewsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 100

    override fun createFragment(position: Int) = when(position) {
        0 -> NewsHomeFragment()
        else -> Fragment()
    }
}