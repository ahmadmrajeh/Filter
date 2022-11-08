package com.example.filter.ui.adapters.nestead

 import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
 import com.example.filter.databinding.ParentItemBinding
import com.example.filter.ui.adapters.nestead.childs.ChildMembersAdapter


class  ParentHolder(private val binding: ParentItemBinding,listener:   (id: Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root){
    var adapterListener: (id: Int) -> Unit = listener
    /*  init {

          itemView.setOnClickListener {
              onItemClick?.invoke(gameOfThronesHouseList[adapterPosition])

          }
      } */

    fun bind(result: FieledRealm?) {
        binding.parentItemTitle .text = result?.name
        val childMembersAdapter = ChildMembersAdapter(result?.options, adapterListener)
        binding.childRecyclerview .layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
        binding.childRecyclerview.adapter = childMembersAdapter

    }

}