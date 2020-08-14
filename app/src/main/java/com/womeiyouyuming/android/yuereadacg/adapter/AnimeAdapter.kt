package com.womeiyouyuming.android.yuereadacg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.annotation.UiThread
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.databinding.ItemAnimeListBinding
import com.womeiyouyuming.android.yuereadacg.model.Anime


/**
 * Created by Yue on 2020/8/5.'
 *
 * Anime 动画列表的adapter
 */
class AnimeAdapter(override val retry: () -> Unit, private val itemClick: (anime: Anime) -> Unit) :
    BasePagedListAdapter<Anime>(AnimeDiffCallback) {

    override val itemLayoutResId = R.layout.item_anime_list


    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {

        val binding =
            ItemAnimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val animeHolder = AnimeHolder(binding)

        //getBindingAdapterPosition   返回数据在自己adapter中的位置
        //getAbsoluteAdapterPosition  返回数据在多个adapter合并后的adapter中的绝对位置
        //getLayoutPosition           返回数据在layoutManager中的位置，
        // 由于有试图缓存，所以layoutPosition可能与adapter不同步，并且在合并的adapter中用有可能出现越界
        //一般用bindingPosition即可，如果在MergerAdapter中想获取绝对位置就用第二个。
        //itemClick(animeHolder.bindingAdapterPosition)

        //这里省事直接传递anime对象了

        animeHolder.itemView.setOnClickListener { itemClick(binding.anime!!) }
        return animeHolder
    }

    override fun bindViewHolder(holder: RecyclerView.ViewHolder, item: Anime?) {
        item?.let { (holder as AnimeHolder).bind(it) }
    }


    class AnimeHolder(val binding: ItemAnimeListBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(anime: Anime) {
            binding.anime = anime
        }

    }

}