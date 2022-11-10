package com.example.filter.ui.adapters.nestead.parentHolders

 import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
 import com.example.filter.databinding.ParentItemBinding
import com.example.filter.ui.adapters.nestead.childs.ChildMembersAdapter
 import java.util.*


class  ParentHolder(
    private val binding: ParentItemBinding,
    listener: (id: Int) -> Unit,
    listener2: (id: Int) -> Unit,
    viewType: Int
) : RecyclerView.ViewHolder(binding.root){
    var adapterListener: (id: Int) -> Unit = listener
    var viewTypeTextOrImg = viewType
    var clickListenerImg: (id: Int) -> Unit =  listener2


    fun bind(result: FieledRealm?) {
        selectLabelLanguage(result)
        val childMembersAdapter = ChildMembersAdapter(result?.options, adapterListener,
            ,viewTypeTextOrImg,clickListenerImg)
        binding.childRecyclerview .layoutManager = LinearLayoutManager(itemView.context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.childRecyclerview.adapter = childMembersAdapter
    }

    private fun selectLabelLanguage(item:FieledRealm?) {
        if (Locale.getDefault().displayLanguage == "English") {
            binding.parentItemTitle.text = item?.label_en
        } else {
            binding.parentItemTitle.text = item?.label_ar
        }
    }


}