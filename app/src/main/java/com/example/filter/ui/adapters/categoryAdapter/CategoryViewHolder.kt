package com.example.filter.ui.adapters.categoryAdapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.datascource.realm.category.CatItemRlm
import com.example.filter.databinding.CategoryBinding
import java.util.*


class CategoryViewHolder(private val binding: CategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(item: CatItemRlm) {

        selectLabelLanguage(item)

        Glide.with(itemView)
            .load( item.icon)
            .into(binding.categoryIcon)
    }


    private fun selectLabelLanguage(item:CatItemRlm) {
        if (Locale.getDefault().displayLanguage == "English") {
            binding.categoryText.text = item.label_en
        } else {
            binding.categoryText.text = item.name
        }
    }


}