package com.womeiyouyuming.android.yuereadacg

import android.os.Build
import android.os.Bundle

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import androidx.navigation.findNavController

import androidx.navigation.ui.setupWithNavController

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //SDK23以下，状态栏文字颜色不会跟着浅色背景改变，因此不能用纯白的状态栏背景，给一个灰色
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            window.statusBarColor = ActivityCompat.getColor(this, R.color.statusBarColorBeforeSDK23)



        //由于处理toolbar和actionbar隐藏显示界面会很乱，所以这里给每一个页面设置自己的toolbar
        val navController = findNavController(R.id.navHosFragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavView.visibility =
                if (destination.id == R.id.nav_news_content || destination.id == R.id.nav_photo) View.GONE else View.VISIBLE
        }

        bottomNavView.setupWithNavController(navController)


    }



}
