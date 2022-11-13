package com.example.filter.ui.adapters.nestead.dialogs.viewholders


import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.IconChildBinding
import com.example.filter.databinding.TextGrandchildBinding
import java.util.*


class ChildHolderIconDialog(private val binding: IconChildBinding ) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(item: RealmOption?) {

   binding.root.setOnClickListener{

    }
        Glide.with(itemView)
            .load( item?.option_img)
            .placeholder(R.drawable.ic_any_check_24)
            .circleCrop()
            .into(binding.iconImg)
        selectLabelLanguage(item)

    }

    private fun selectLabelLanguage(item:RealmOption?) {

        if (Locale.getDefault().displayLanguage == "English") {
           binding.text.text = item?.label_en
        } else {
            binding.text .text = item?.label
        }
    }

}