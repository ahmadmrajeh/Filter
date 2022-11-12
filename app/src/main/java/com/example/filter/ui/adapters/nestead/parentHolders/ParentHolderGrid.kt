package com.example.filter.ui.adapters.nestead.parentHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
import com.example.filter.databinding.GridParentBinding
import com.example.filter.ui.adapters.nestead.childs.GridAdapter
import com.google.android.flexbox.*
import java.util.*


class  ParentHolderGrid(private val binding: GridParentBinding, listener:   (id: Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root){
    var adapterListener: (id: Int) -> Unit = listener


    fun bind(result: FieledRealm?) {
        selectLabelLanguage(result)
        val childMembersAdapter = GridAdapter(result?.options, adapterListener)
        val layoutManager = FlexboxLayoutManager(itemView.context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        layoutManager.flexWrap=FlexWrap.WRAP
layoutManager.alignItems = AlignItems.CENTER




        binding.childRecyclerview.adapter = childMembersAdapter
        binding.childRecyclerview .layoutManager =  layoutManager

    }
    private fun selectLabelLanguage(item:FieledRealm?) {
        if (Locale.getDefault().displayLanguage == "English") {
            binding.parentItemTitle.text = item?.label_en
        } else {
            binding.parentItemTitle.text = item?.label_ar
        }
    }
}
