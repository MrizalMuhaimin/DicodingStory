package com.example.dicodingstory.presentation.adabter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingstory.R
import com.example.dicodingstory.data.db.entities.ListStoryEntity
import com.example.dicodingstory.databinding.ActivityDetailStoryBinding
import com.example.dicodingstory.databinding.FragmentStoryBinding
import com.example.dicodingstory.presentation.adabter.viewholder.ListStoryViewHolder
import com.example.dicodingstory.presentation.adabter.viewholder.StoryViewHolder
import com.example.dicodingstory.presentation.intent.DetailStoryActivity
import com.example.dicodingstory.presentation.intent.DicodingStoryActivity


class StoryAdapter :
    PagingDataAdapter<ListStoryEntity, StoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StoryViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return StoryViewHolder(
            inflater.inflate(R.layout.fragment_story,viewGroup,false)
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val storyData = getItem(position)
        if (storyData == null) {
            Log.e("TAG", "onBindViewHolder: Data is empty")
        } else {
            holder.onBind(storyData)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryEntity>() {
            override fun areItemsTheSame(oldItem: ListStoryEntity, newItem: ListStoryEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryEntity, newItem: ListStoryEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}