package com.example.filter.ui.adapters.nestead.childs.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
import com.example.filter.databinding.ParentNumricBinding


class  ParentHolderNumeric(private val binding: ParentNumricBinding,listener:   (id: Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root){
    var adapterListener: (id: Int) -> Unit = listener



    fun bind(result: FieledRealm?) {
        binding.parentItemTitle .text = result?.name
     /*   val childMembersAdapter = ChildMembersAdapter(result?.options, adapterListener)
        binding.childRecyclerview .layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
        binding.childRecyclerview.adapter = childMembersAdapter
*/
    }

}