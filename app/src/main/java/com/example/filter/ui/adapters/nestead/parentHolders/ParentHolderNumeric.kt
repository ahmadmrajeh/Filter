package com.example.filter.ui.adapters.nestead.parentHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.databinding.ParentNumricBinding
import io.realm.RealmList
import java.util.*


class  ParentHolderNumeric(private val binding: ParentNumricBinding,listener:    (params: List<Any>) -> Unit) :
    RecyclerView.ViewHolder(binding.root){
    var adapterListener: (params: List<Any>) -> Unit = listener



    fun bind(result: FieledRealm?) {
        selectLabelLanguage(result)
        binding.From .setOnClickListener {
            adapterListener(listOf(result!!.options,"numericFrom") )
        }

        binding.To.setOnClickListener {
            adapterListener(listOf(result!!.options,"numericTo") )
         }


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
