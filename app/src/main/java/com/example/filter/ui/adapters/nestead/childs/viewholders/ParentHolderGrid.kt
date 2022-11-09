package com.example.filter.ui.adapters.nestead.childs.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
import com.example.filter.databinding.GridParentBinding
import com.example.filter.ui.adapters.nestead.childs.GridAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class  ParentHolderGrid(private val binding: GridParentBinding, listener:   (id: Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root){
    var adapterListener: (id: Int) -> Unit = listener


    fun bind(result: FieledRealm?) {
        binding.parentItemTitle .text = result?.name
         val childMembersAdapter = GridAdapter(result?.options, adapterListener)

        val layoutManager = FlexboxLayoutManager(itemView.context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        binding.childRecyclerview.adapter = childMembersAdapter
        binding.childRecyclerview .layoutManager =  layoutManager
    }

}
