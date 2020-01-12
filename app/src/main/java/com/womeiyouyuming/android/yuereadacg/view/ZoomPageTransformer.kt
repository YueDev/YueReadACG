package com.womeiyouyuming.android.yuereadacg.view

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.absoluteValue

/**
 * Created by Yue on 2020/1/10.
 * viewpager2的滑动缩放效果  https://github.com/YueDev/ViewPager2
 *
 *
 */


class ZoomPageTransformer : ViewPager2.PageTransformer {


    private val minScan = 0.90f

    //position 当页面居中显示时为0，滑出左边屏幕为-1，滑出右边屏幕为1
    override fun transformPage(page: View, position: Float) {

        page.scaleY = when {
            position < -1 -> {
                minScan
            }
            position <= 1 -> {
                val rate = 1.0f - position.absoluteValue / 1.0f
                minScan + rate * (1.0f - minScan)
            }
            else -> {
                minScan
            }
        }


    }
}