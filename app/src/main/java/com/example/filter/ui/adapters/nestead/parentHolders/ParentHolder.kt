package com.example.filter.ui.adapters.nestead.parentHolders

 import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
 import com.example.filter.databinding.ParentItemBinding
import com.example.filter.ui.adapters.nestead.childs.ChildMembersAdapter
 import java.util.*

class  ParentHolder(
    private val binding: ParentItemBinding,
    listener:(params: List<Any>) -> Unit,
    listener2: (params: List<Any>) -> Unit,
    viewType: Int
) : RecyclerView.ViewHolder(binding.root){
    var adapterListener: (params: List<Any>) -> Unit = listener
    var viewTypeTextOrImg = viewType
    var clickListenerImg: (params: List<Any>) -> Unit =  listener2
    var type = viewType


    fun bind(result: FieledRealm?) {
        selectLabelLanguage(result )
        circleMembersAdapter(result)
        binding.selectedClicked.setOnClickListener {
        if (type== 3) {
            clickListenerImg(listOf(result!!.options,"icon") )
        } else {
            adapterListener(listOf(result!!.options,"string") )
        }
    }
 }


    private fun circleMembersAdapter(result: FieledRealm?) {
        val childMembersAdapter = ChildMembersAdapter(
            result?.options, adapterListener, viewTypeTextOrImg, clickListenerImg
        )
        binding.childRecyclerview.layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.HORIZONTAL, false
        )
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