package com.womeiyouyuming.android.yuereadacg.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.womeiyouyuming.android.yuereadacg.fragment.news.NewsAnimeFragment
import com.womeiyouyuming.android.yuereadacg.fragment.news.NewsHomeFragment
import com.womeiyouyuming.android.yuereadacg.util.NEWS_PAGE_LIST

/**
 * Created by Yue on 2020/1/9.
 */
class NewsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = NEWS_PAGE_LIST.size

    override fun createFragment(position: Int) = when(position) {
        0 -> NewsHomeFragment()
        1 -> NewsAnimeFragment()
        else -> Fragment()
    }
}