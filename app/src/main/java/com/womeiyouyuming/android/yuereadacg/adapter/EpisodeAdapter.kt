package com.womeiyouyuming.android.yuereadacg.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.model.Play
import kotlinx.android.synthetic.main.item_episode.view.*

/**
 * Created by Yue on 2020/8/13.
 */
class EpisodeAdapter(private val itemClick: (index: Int) -> Unit) :
    ListAdapter<Play, EpisodeAdapter.EpisodeHolder>(PlayDiffCallback) {


    private var selectIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        val holder = EpisodeHolder(itemView)

        holder.episodeButton.setOnClickListener {
            if (selectIndex == holder.bindingAdapterPosition) return@setOnClickListener
            selectIndex = holder.bindingAdapterPosition
            itemClick(selectIndex)
            notifyDataSetChanged()
        }

        return holder
    }

    override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
        val isSelect = position == selectIndex
        holder.onBind(getItem(position).name, position, isSelect)
    }






    class EpisodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val episodeButton: MaterialButton = itemView.findViewById(R.id.episodeButton)


        fun onBind(name: String, position: Int, isSelect: Boolean) {
            episodeButton.text = "第 ${position + 1} 话\n${name}"
            setSelectState(isSelect)
        }

        private fun setSelectState(isSelect: Boolean) {
            val colorResId = if (isSelect) R.color.colorPrimary else R.color.darkGrayAndroid
            val color = episodeButton.resources.getColor(colorResId)
            episodeButton.setStrokeColorResource(colorResId)
            episodeButton.setTextColor(color)
        }


    }

}