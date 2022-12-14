package com.example.filter.ui.adapters.nestead.dialogs.viewholders


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.databinding.TextGrandchildBinding
import io.realm.RealmList
import java.util.*


class ChildHolderTextDialog(
    private val binding: TextGrandchildBinding,
    clickListener:(option:RealmOption, isSelected:Boolean) -> Unit,
    passedSelectedOptions: RealmList<RealmOption>
) :
    RecyclerView.ViewHolder(binding.root) {
    var listener  = clickListener
    var selected=   passedSelectedOptions

    fun bind(item: RealmOption?) {
        binding.root.setOnClickListener{
        }
            selectLabelLanguage(item)
        if (item != null) {
            binding.checkBox.isChecked = item.isSelected
        }
            binding.root.visibility = View.VISIBLE
            binding.checkBox.setOnClickListener {
                handleClick(item)
            }
    }

    private fun handleClick(item: RealmOption?) {
        if (item != null) {
            if (item.isSelected) {
                binding.checkBox.isChecked = false
                listener(item, false)
            } else {
                binding.checkBox.isChecked = true
                listener(item, true)
            }
        }
    }

    private fun selectLabelLanguage(item:RealmOption?) {
        if (Locale.getDefault().displayLanguage == "English") {
           binding.text.text = item?.label_en
        } else {
            binding.text .text = item?.label
        }
    }

}