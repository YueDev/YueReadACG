//package com.womeiyouyuming.android.yuereadacg
//
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.MenuItem
//import android.widget.*
//import androidx.core.app.ActivityCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.womeiyouyuming.android.yuereadacg.adapter.EpisodeAdapter
//import com.womeiyouyuming.android.yuereadacg.model.Anime
//import com.womeiyouyuming.android.yuereadacg.util.getRandomAvatar
//import com.womeiyouyuming.android.yuereadacg.view.MyPrepareView
//import com.womeiyouyuming.android.yuereadacg.view.MyVodControlView
//import kotlinx.android.synthetic.main.activity_play.*
//import kotlinx.android.synthetic.main.toolbar.*
//import xyz.doikki.videocontroller.StandardVideoController
//import xyz.doikki.videocontroller.component.CompleteView
//import xyz.doikki.videocontroller.component.ErrorView
//import xyz.doikki.videocontroller.component.GestureView
//import xyz.doikki.videocontroller.component.TitleView
//import xyz.doikki.videoplayer.player.VideoViewManager
//
//class PlayActivity : AppCompatActivity() {
//
//
//    //由于要适配横屏，所以把播放器单独写在一个activity里了
//    //并没有用navigation管理PlayActivity，因为两个activity navigation容易乱。所以界面也写在这里边了。
//
//    private val anime by lazy {
//        intent.getSerializableExtra("anime") as Anime
//    }
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_play)
//
//
//        //SDK23以下，状态栏文字颜色不会跟着浅色背景改变，因此不能用纯白的状态栏背景，给一个灰色
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
//            window.statusBarColor = ActivityCompat.getColor(this, R.color.statusBarColorBeforeSDK23)
//        initView()
//    }
//
//    private fun initView() {
//
//        initToolbar()
//
//        initRecyclerView()
//
//        Glide.with(avatarImageView).load(getRandomAvatar()).circleCrop().into(avatarImageView)
//        titleTextView.text = anime.title
//        authorTextView.text = anime.author
//        contentTextView.maxLines = 10
//        contentTextView.text = anime.content
//
//        initVideoView()
//
//    }
//
//
//
//    override fun onBackPressed() {
//        if (VideoViewManager.instance().get("PlayActivity")?.onBackPressed() == true) return
//        super.onBackPressed()
//    }
//
//    private fun initToolbar() {
//        toolBar.title = resources.getString(R.string.lab_play)
//
//        toolBar.setNavigationOnClickListener {
//            finish()
//        }
//
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            finish()
//        }
//        return true
//    }
//
//    private fun initRecyclerView() {
//        val playList = anime.playlist
//        episodeRecyclerView.layoutManager =
//            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
//        val adapter = EpisodeAdapter {
//
//            videoView.release()
//            videoView.setUrl(anime.playlist[it].url)
//            videoView.findViewById<TextView>(R.id.title).text = anime.playlist[it].name
//            videoView.start()
//        }
//        episodeRecyclerView.adapter = adapter
//        adapter.submitList(playList)
//
//    }
//
//    private fun initVideoView() {
//        lifecycle.addObserver(videoView)
//
//
//        //init videoView，下边的组件组号都要，负责容易出错
//        val prepareView = MyPrepareView(this)
//        //设置播放器封面，bilibil没设置，这里我们也不了
//        //getImgFromUrl(prepareView.findViewById(R.id.thumb), anime.img)
//        prepareView.setClickStart()
//
//        val controller = StandardVideoController(this)
//        controller.findViewById<ProgressBar>(R.id.loading).indeterminateDrawable = resources.getDrawable(R.drawable.my_dkplayer_progress_loading, null)
//
//        val completeView = CompleteView(this)
//        completeView.findViewById<ImageView>(R.id.iv_replay).setBackgroundResource(R.drawable.my_dkplayer_shape_play_bg)
//
//        val errorView = ErrorView(this)
//        errorView.findViewById<TextView>(R.id.status_btn).setBackgroundResource(R.drawable.my_dkplayer_shape_status_view_btn)
//
//        val gestureView = GestureView(this)
//        gestureView.findViewById<ProgressBar>(R.id.pro_percent).progressDrawable = resources.getDrawable(R.drawable.my_dkplayer_layer_progress_bar, null)
//
//        val titleView = TitleView(this)
//
//
//        val vodControlView = MyVodControlView(this)
//
//        controller.addControlComponent(
//            completeView,
//            errorView,
//            gestureView,
//            prepareView,
//            titleView,
//            vodControlView
//        )
//
//
//        controller.setEnableOrientation(true)
//
//        VideoViewManager.instance().add(videoView, "PlayActivity")
//        videoView.setVideoController(controller)
//        videoView.setUrl(anime.playlist[0].url)
//        titleView.setTitle(anime.playlist[0].name)
//        videoView.start()
//    }
//
//}