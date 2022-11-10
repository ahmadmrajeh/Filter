package com.example.filter.ui.adapters.nestead.parentHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
import com.example.filter.databinding.ParentNumricBinding
import java.util.*


class  ParentHolderNumeric(private val binding: ParentNumricBinding,listener:   (id: Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root){
    var adapterListener: (id: Int) -> Unit = listener



    fun bind(result: FieledRealm?) {
        selectLabelLanguage(result)


     /*   val childMembersAdapter = ChildMembersAdapter(result?.options, adapterListener)
        binding.childRecyclerview .layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
        binding.childRecyclerview.adapter = childMembersAdapter
          */

    }

    private fun selectLabelLanguage(item:FieledRealm?) {
        if (Locale.getDefault().displayLanguage == "English") {
            binding.parentItemTitle.text = item?.label_en
        } else {
            binding.parentItemTitle.text = item?.label_ar
        }
    }
}