package com.example.filter.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.filter.databinding.CategoryBinding
import com.example.filter.realm.CatItemRlm

class ViewHolder(private val binding: CategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(item: CatItemRlm) {


        binding.categoryText .text= item.name


        Glide.with(itemView)
            .load( item.icon)
            .into(binding.categoryIcon)


      /*  binding.message.text = item.text
        MessageViewHolder.setTextColor(item.name, binding.message, currentUserName)
        binding.username.text = item.name ?: MainActivity.ANONYMOUS
        if (item.photoUrl != null) {
            loadImageIntoView(binding.messengerImageView, item.photoUrl)
        } else {
            binding.messengerImageView.setImageResource(R.drawable.ic_account_circle_black_36dp)
        }*/

    }


}