package com.womeiyouyuming.android.yuereadacg

import android.app.Application
import com.dueeeke.videoplayer.exo.ExoMediaPlayerFactory
import com.dueeeke.videoplayer.player.VideoViewConfig
import com.dueeeke.videoplayer.player.VideoViewManager

/**
 * Created by Yue on 2020/8/11.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        VideoViewManager.setConfig(
            VideoViewConfig.newBuilder()
            .setPlayerFactory(ExoMediaPlayerFactory.create())
            .build())

    }

}