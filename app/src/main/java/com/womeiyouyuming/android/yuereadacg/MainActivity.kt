package com.womeiyouyuming.android.yuereadacg

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dueeeke.videoplayer.player.VideoViewManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val navController by lazy {
        findNavController(R.id.navHosFragment)
    }


    private val appBarConfiguration by lazy {
        AppBarConfiguration(navController.graph)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //SDK23以下，状态栏文字颜色不会跟着浅色背景改变，因此不能用纯白的状态栏背景，给一个灰色
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            window.statusBarColor = ActivityCompat.getColor(this, R.color.statusBarColorBeforeSDK23)

        setSupportActionBar(toolBar)


        //资讯页不显示toolbar
        navController.addOnDestinationChangedListener { _, destination, _ ->

            toolBar.visibility = if (destination.id == R.id.nav_news) View.GONE else View.VISIBLE

            bottomNavView.visibility = if (destination.id == R.id.nav_news_content) View.GONE else View.VISIBLE



        }



        //nav与tooBar和bottomNavView联动,这里不能用toolbar的setupActionBarWithNavController，会出问题。
        // 因为toolbar已经被设置成actionbar，所以用setupActionBarWithNavController

        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavView.setupWithNavController(navController)

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
