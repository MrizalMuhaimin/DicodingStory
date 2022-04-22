package com.example.dicodingstory.presentation.adabter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dicodingstory.R
import com.example.dicodingstory.data.model.data.ListStory
import com.example.dicodingstory.presentation.adabter.viewholder.ListStoryViewHolder

class ListStoryAdapter(private val listStory: MutableList<ListStory>?):
    RecyclerView.Adapter<ListStoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListStoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ListStoryViewHolder(
            inflater.inflate(R.layout.fragment_story,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ListStoryViewHolder, position: Int) {
        holder.onBind(listStory!![position])

    }

    override fun getItemCount(): Int {
        return listStory?.size?:0
    }



}