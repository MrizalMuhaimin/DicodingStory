package com.example.dicodingstory.presentation.adabter.viewholder

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingstory.R
import com.example.dicodingstory.data.model.data.ListStory
import com.example.dicodingstory.presentation.intent.DetailStoryActivity
import com.example.dicodingstory.presentation.intent.DicodingStoryActivity
import de.hdodenhof.circleimageview.CircleImageView

class ListStoryViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val tvUsename: TextView = view.findViewById(R.id.tv_username)
    private val ivStory:ImageView = view.findViewById(R.id.iv_story)
    private val tvCreateAt: TextView = view.findViewById(R.id.tv_createAt)
    private val profile: CircleImageView = view.findViewById(R.id.iv_user_image)

    val context = view.context

    @SuppressLint("SetTextI18n")
    fun onBind(element:ListStory){
        Glide.with(context)
            .load(element.photoUrl)
            .centerCrop()
            .into(ivStory)
        tvUsename.text = element.name
        tvCreateAt.text = "Create At: ${element.createdAt}"

        itemView.setOnClickListener {

            val intent = Intent(itemView.context, DetailStoryActivity::class.java)
            intent.putExtra(DicodingStoryActivity.TAG_DETAIL_STORY, element)

            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(tvUsename,"name1"),
                    Pair(ivStory,"photo1"),
                    Pair(tvCreateAt,"createAt1"),
                    Pair(profile,"profile1")
                )
            itemView.context.startActivity(intent, optionsCompat.toBundle())

        }


    }
}