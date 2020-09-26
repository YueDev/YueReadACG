package com.womeiyouyuming.android.yuereadacg.fragment

import android.Manifest
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.permissionx.guolindev.PermissionX
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.util.savePhotoWithBitmap
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.android.synthetic.main.toolbar.*


/**
 *
 */


class PhotoFragment : Fragment(), Toolbar.OnMenuItemClickListener {


    private var isRefresh = false

    private val imgUrl by lazy {
        requireArguments().getString("imgUrl")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_photo, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initAppBar()
        initToolbar()
        initPhotoView()


    }

    override fun onResume() {
        super.onResume()

        //onResume后自动进入全屏
        //这里需要等到标题栏获取自己的高度后执行
        appBarLayout.post {
            enableFullScreen()
        }
    }


    //弹出菜单 上下文菜单
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        requireActivity().menuInflater.inflate(R.menu.menu_photo, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        onMenuItemClick(item)

        return super.onContextItemSelected(item)
    }


    //菜单，注意是是toolbar的
    override fun onMenuItemClick(item: MenuItem?) = when (item?.itemId) {
        R.id.menu_item_refresh -> {
            isRefresh = true
            loadPhoto()
            true
        }
        R.id.menu_item_save -> {
            savePhoto()
            true
        }
//        R.id.menu_item_wallpaper -> {
//            Toast.makeText(requireContext(), "壁纸点击了", Toast.LENGTH_SHORT).show()
//            true
//        }
//        R.id.menu_item_share -> {
//
//            true
//        }

        else -> false
    }


    private fun initPhotoView() {

        loadPhoto()

        //注册上下文菜单
        registerForContextMenu(photoView)

        photoView.setOnClickListener {

            if (requireView().systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                enableFullScreen()
            } else {
                disableFullscreen()
            }

        }

        //PhotoView自己实现了一个手势监听，会把长按消费掉，因此系统的长按出现上下文菜单不会出现
        //需要在photoView的长按监听里手动弹出上下文菜单
        photoView.setOnLongClickListener {
            it.showContextMenu()
            true
        }
    }


    private fun initToolbar() {
        //设置toolbar
        toolBar.title = resources.getString(R.string.lab_photo)

        toolBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        toolBar.inflateMenu(R.menu.menu_photo)
        toolBar.setOnMenuItemClickListener(this)

    }

    private fun initAppBar() {


        //设置后标题栏才不会被状态栏给遮挡
        appBarLayout.fitsSystemWindows = true


        //保持布局稳定 需要先设置 要不photoView在全屏的时候会重置位置
        requireView().systemUiVisibility =
            requireView().systemUiVisibility or (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
        //响应全屏的监听
        appBarLayout.setOnSystemUiVisibilityChangeListener { visibility ->

            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                ViewCompat.animate(appBarLayout).translationY(0f)
            } else {
                ViewCompat.animate(appBarLayout).translationY(-appBarLayout.height.toFloat())
            }
        }
    }


    //开启全屏显示(沉浸模式) 位操作,这里用位操作来保证systemUiVisibility原本内容不受到改变
    //这里用的是当前fragment的view来全屏，对其他页面没有影响，并且退出fragment也不需要做处理
    private fun enableFullScreen() {

        requireView().systemUiVisibility =
            requireView().systemUiVisibility or (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
    }

    //退出全屏显示
    private fun disableFullscreen() {
        requireView().systemUiVisibility =
            requireView().systemUiVisibility and (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    ).inv()
    }


    //检查存储权限后 存储
    private fun savePhoto() {
        //api小于29，需要获取存储权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            PermissionX
                .init(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        saveImage()
                    } else {
                        Toast.makeText(requireContext(), "没有存储权限-_-", Toast.LENGTH_LONG).show()
                    }

                }

        } else {
            saveImage()
        }
    }

    //存储图片，需要先获取存储权限，glide加载完成后才会存储bitmap
    //由于glide有缓存，一般情况不会耗时
    private fun saveImage() {
        Glide.with(this).asBitmap().load(imgUrl).addListener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                Toast.makeText(requireContext(), "出错了，请稍后再试", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                resource ?: let {
                    Toast.makeText(requireContext(), "出错了，请稍后再试", Toast.LENGTH_SHORT).show()
                    return false
                }

                savePhotoWithBitmap(requireContext(), resource, imgUrl!!.substringAfterLast("/")) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                return false
            }
        }).preload()
    }


    //加载图片，第一次加载 和 刷新 用
    private fun loadPhoto() {

        Glide.with(photoView).load(imgUrl).addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Toast.makeText(requireContext(), "出错了，请稍后再试", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                //第一次加载不显示提示，只有刷新的时候才显示
                if (isRefresh) Toast.makeText(requireContext(), "图片加载成功", Toast.LENGTH_SHORT).show()
                return false
            }
        }).into(photoView)

    }


}