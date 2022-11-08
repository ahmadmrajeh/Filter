package com.example.filter.ui.adapters.nestead.childs

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.ChildItemBinding


class ChildHolderOne(private val binding: ChildItemBinding, context: Context) :
    RecyclerView.ViewHolder(binding.root) {
var thiscontext =context

    fun bind(item: RealmOption?) {

        binding.textView2 .text=item?.label_en

      //  binding.constraint.background = ContextCompat.getDrawable(thiscontext, R.drawable.circle_selected_option_bg)


    }



}