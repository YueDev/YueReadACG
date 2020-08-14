package com.womeiyouyuming.android.yuereadacg.view

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.dueeeke.videoplayer.player.AbstractPlayer
import com.dueeeke.videoplayer.player.VideoView

/**
 * Created by Yue on 2020/7/27.
 */
class MyVideoView<P : AbstractPlayer> : VideoView<P>, LifecycleObserver {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )



    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        resume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory() {
        release()
    }

}