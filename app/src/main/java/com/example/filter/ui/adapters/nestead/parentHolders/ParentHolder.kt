package com.example.filter.ui.adapters.nestead.parentHolders

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.databinding.ParentItemBinding
 import com.example.filter.ui.adapters.nestead.childs.ChildMembersAdapter
import io.realm.RealmList
import java.util.*

class ParentHolder(
    private val binding: ParentItemBinding,
    listener: (params: List<Any>) -> Any,
    listener2: (params: List<Any>) -> Any,
    listener3: (params: List<Any>) -> Any,
    listener4: (params: List<Any>) -> Any,
    listener5: (params: List<Any>) -> Any,
    viewType: Int,
    passedSelectedOptions: RealmList<RealmOption>
) : RecyclerView.ViewHolder(binding.root) {
    private var viewTypeTextOrImg = viewType
    var adapterListener: (params: List<Any>) -> Any = listener
    var clickListenerImg: (params: List<Any>) -> Any = listener2
    var adapterListenerDialog: (params: List<Any>) -> Any = listener3
    var clickListenerImgDialog: (params: List<Any>) -> Any = listener4
    var type = viewType
    var realmLiveOptions = passedSelectedOptions
    var filteredRealmField = listener5

    fun bind(item: FieledRealm) {

         filteredRealmField(listOf(item))



        selectLabelLanguage(item)
        circleMembersAdapter(item.options)
        binding.selectedClicked.setOnClickListener {
            if (type == 3) {
                clickListenerImgDialog(listOf(item.options, "icon"))
            } else {
                adapterListenerDialog(listOf(item.options, "string"))
            }
        }


        var sumText = ""
        for (items in realmLiveOptions) {
            items?.let {
                if (it.option_img.isNullOrEmpty() && viewTypeTextOrImg == 1
                    && it.label_en != "Any" && item.id.toString() == it.field_id
                )
                    sumText += it.label_en + ", "
                else if (viewTypeTextOrImg == 3 && item.id.toString() == it.field_id)
                    sumText += it.label_en + ", "
                else if (it.label_en != "Any" && item.id.toString() == it.field_id) sumText =
                    "Any"
            }
        }
        binding.selected.text = sumText
    }


    private fun circleMembersAdapter(result: RealmList<RealmOption>) {
        val childMembersAdapter = ChildMembersAdapter(
            result, adapterListener, viewTypeTextOrImg, clickListenerImg, realmLiveOptions
        )
        binding.childRecyclerview.layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.HORIZONTAL, false
        )
        binding.childRecyclerview.adapter = childMembersAdapter
    }

    private fun selectLabelLanguage(item: FieledRealm?) {
        if (Locale.getDefault().displayLanguage == "English") {
            binding.parentItemTitle.text = item?.label_en
        } else {
            binding.parentItemTitle.text = item?.label_ar
        }
    }


}