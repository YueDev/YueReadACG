package com.womeiyouyuming.android.yuereadacg.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.network.NetworkState

/**
 * Created by Yue on 2020/1/10.
 */
@BindingAdapter("imgUrl")
fun getImgFromUrl(imageView: ImageView, url: String?) {

    url ?: return
    Glide.with(imageView).load(url).placeholder(R.drawable.ic_placeholder).into(imageView)


}


//Amlyu网站防盗链，因此需要加Headers请求头
@BindingAdapter("imgUrlAmlyu")
fun getImgFromAmlyu(imageView: ImageView, url: String?) {

    url ?: return
    val glideUrl = GlideUrl(url) { hashMapOf("Referer" to "https://amlyu.com/") }
    Glide.with(imageView).load(glideUrl).placeholder(R.drawable.ic_placeholder).into(imageView)


}


@BindingAdapter("showWhenLoading")
fun showWhenLoading(view: View, networkState: NetworkState?) {

    view.visibility = if (networkState == NetworkState.LOADING) View.VISIBLE else View.GONE

}


@BindingAdapter("showWhenFailed")
fun showWhenFailed(view: View, networkState: NetworkState?) {

    view.visibility = if (networkState == NetworkState.FAILED) View.VISIBLE else View.GONE

}