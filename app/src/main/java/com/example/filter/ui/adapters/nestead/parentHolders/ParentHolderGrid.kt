package com.example.filter.ui.adapters.nestead.parentHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.databinding.GridParentBinding
import com.example.filter.ui.adapters.nestead.childs.GridAdapter
import com.google.android.flexbox.*
import io.realm.RealmList
import java.util.*


class  ParentHolderGrid(
    private val binding: GridParentBinding,
    listener: ( option:RealmOption, isSelected:Boolean ) -> Unit,
    passedSelectedOptions: RealmList<RealmOption>
) :
    RecyclerView.ViewHolder(binding.root) {
    var adapterListener = listener
    var realmLiveOptions = passedSelectedOptions

    fun bind(result: FieledRealm) {
        selectLabelLanguage(result)
        val childMembers = GridAdapter(result.options,
                            adapterListener ,realmLiveOptions)
        val layoutManager = FlexboxLayoutManager(itemView.context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.flexWrap=FlexWrap.WRAP
        binding.childRecyclerview.adapter = childMembers
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
