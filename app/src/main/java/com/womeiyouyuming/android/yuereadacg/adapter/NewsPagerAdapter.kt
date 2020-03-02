package com.womeiyouyuming.android.yuereadacg.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.womeiyouyuming.android.yuereadacg.fragment.news.*
import com.womeiyouyuming.android.yuereadacg.util.NEWS_PAGE_LIST

/**
 * Created by Yue on 2020/1/9.
 *
 * 整个资讯页的adapter
 */
class NewsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = NEWS_PAGE_LIST.size

    override fun createFragment(position: Int) = when(position) {
        0 -> NewsHomeFragment()
        1 -> NewsAnimeFragment()
        2 -> NewsExpoFragment()
        3 -> NewsBeautyFragment()
        4 -> NewsFigmaFragment()
        5 -> NewsSpecialFragment()
        6 -> NewsBaguaFragment()
        7 -> NewsGameFragment()
        else -> throw IllegalArgumentException("No such page[$position]")
    }
}