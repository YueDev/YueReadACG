package com.womeiyouyuming.android.yuereadacg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.databinding.ItemAnimeListBinding
import com.womeiyouyuming.android.yuereadacg.databinding.ItemGalleryBinding
import com.womeiyouyuming.android.yuereadacg.model.Anime
import com.womeiyouyuming.android.yuereadacg.model.Photo

/**
 * Created by Yue on 2020/8/14.
 */
class GalleryAdapter(override val retry: () -> Unit, private val itemClick: (imgUrl: String) -> Unit) :
    BasePagedListAdapter<Photo>(PhotoDiffCallback) {


    override val itemLayoutResId = R.layout.item_gallery

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = GalleryHolder(binding)
        holder.itemView.setOnClickListener {
            itemClick(binding.photo!!.url)
        }
        return holder
    }

    override fun bindViewHolder(holder: RecyclerView.ViewHolder, item: Photo?) {
        item?.let { (holder as GalleryHolder).bind(it) }
    }

    class GalleryHolder(val binding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            binding.photo = photo
        }

    }

}