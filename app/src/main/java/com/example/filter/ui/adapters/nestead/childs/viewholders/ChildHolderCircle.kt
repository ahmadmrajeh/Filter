package com.example.filter.ui.adapters.nestead.childs.viewholders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.ChildItemBinding


class ChildHolderCircle(private val binding: ChildItemBinding, context: Context) :
    RecyclerView.ViewHolder(binding.root) {
var thiscontext =context

    fun bind(item: RealmOption?) {

   //   binding.constraint.background = ContextCompat.getDrawable(thiscontext, R.drawable.circle_selected_option_bg)

        Glide.with(itemView)
            .load( item?.option_img)
            .placeholder(R.drawable.ic_launcher_foreground)
            .circleCrop()
            .into(binding.imageView)





    }



}