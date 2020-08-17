package com.womeiyouyuming.android.yuereadacg.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.adapter.getImgFromAmlyu
import kotlinx.android.synthetic.main.fragment_photo.*


/**
 *
 */


class PhotoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_photo, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgUrl = requireArguments().getString("imgUrl")

        getImgFromAmlyu(photoView, imgUrl)

        photoView.setOnClickListener {

            if (requireActivity().window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {

                enableFullScreen()
            } else {
                disableFullscreen()
            }
        }


    }


    //开启全屏显示(沉浸模式) 位操作,这里用位操作来保证不破坏其他页面设置的的systemUiVisibility
    //如果其他页面没有设置过systemUiVisibility，可以直接赋值，逻辑更清晰

    private fun enableFullScreen() {
        requireActivity().window.decorView.systemUiVisibility =
            requireActivity().window.decorView.systemUiVisibility or (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
    }

    //退出全屏显示
    private fun disableFullscreen() {
        requireActivity().window.decorView.systemUiVisibility =
            requireActivity().window.decorView.systemUiVisibility and (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    ).inv()
    }



}