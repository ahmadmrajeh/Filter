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
    listener: (option: RealmOption, isSelected:Boolean) -> Unit,
    listener2:(option: RealmOption, isSelected:Boolean) -> Unit,
    listener3: (options:RealmList<RealmOption>, fromWhere:String) -> Unit,
    listener4: (options:RealmList<RealmOption>, fromWhere:String) -> Unit,

    viewType: Int,
    passedSelectedOptions: RealmList<RealmOption>
) : RecyclerView.ViewHolder(binding.root) {

    private var viewTypeTextOrImg = viewType
    var adapterListener = listener
    var clickListenerImg = listener2
    var adapterListenerDialog = listener3
    var clickListenerImgDialog = listener4
    var type = viewType
    private var realmLiveOptions = passedSelectedOptions


    fun bind(item: FieledRealm) {
        selectLabelLanguage(item)
        circleMembersAdapter(item)
        binding.selectedClicked.setOnClickListener {
            if (type == 3) {
                clickListenerImgDialog(item.options, "icon")
            } else {
                adapterListenerDialog(item.options, "string")
            }
        }

   var sumText = ""
        for (items in realmLiveOptions) {
            items?.let {
                if (it.option_img.isNullOrEmpty() && viewTypeTextOrImg == 1
                    && it.label_en != "Any" && item.id.toString() == it.field_id
                )
                    sumText += it.label_en + ", "
                else if (viewTypeTextOrImg == 3 && item.id.toString() == it.field_id   && it.label_en != "Any")
                    sumText += it.label_en + ", "
                else if (it.label_en == "Any" && item.id.toString() == it.field_id) sumText =
                    "Any"
            }
        }
        binding.selected.text = sumText
    }

    private fun circleMembersAdapter(result:FieledRealm ) {
        val childMembersAdapter = ChildMembersAdapter(
            result.options, adapterListener

            , viewTypeTextOrImg, clickListenerImg, realmLiveOptions
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