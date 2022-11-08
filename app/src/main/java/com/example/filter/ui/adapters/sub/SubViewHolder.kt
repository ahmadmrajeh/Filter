package com.example.filter.ui.adapters.sub

 import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
 import com.example.filter.databinding.SubCategoryBinding

 import com.example.filter.realm.category.SubCatRealm
import java.util.*

class SubViewHolder(private val binding: SubCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(item: SubCatRealm) {


        selectLabelLanguage(item)


        Glide.with(itemView)
            .load( item.icon)
            .into(binding.categoryIcon)




    }

    private fun selectLabelLanguage(item: SubCatRealm) {
        if (Locale.getDefault().displayLanguage == "English") {
            binding.categoryText.text = item.label_en
        } else {

            binding.categoryText.text = item.name
        }
    }


}