package com.womeiyouyuming.android.yuereadacg

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.womeiyouyuming.android.yuereadacg.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            window.statusBarColor = ActivityCompat.getColor(this, R.color.statusBarColorBeforeSDK23)


        bottomNavView.setupWithNavController(findNavController(R.id.navHosFragment))


    }
}
