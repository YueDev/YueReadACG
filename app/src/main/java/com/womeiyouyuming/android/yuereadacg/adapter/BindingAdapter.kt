package com.womeiyouyuming.android.yuereadacg.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.womeiyouyuming.android.yuereadacg.R

/**
 * Created by Yue on 2020/1/10.
 */
@BindingAdapter("app:imgUrl")
fun getImgFromUrl(imageView: ImageView, url: String?) {

    Glide.with(imageView).load(url).placeholder(R.drawable.ic_placeholder).into(imageView)

}
