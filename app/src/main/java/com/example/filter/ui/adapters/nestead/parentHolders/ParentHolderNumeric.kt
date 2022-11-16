package com.example.filter.ui.adapters.nestead.parentHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.databinding.ParentNumricBinding
import io.realm.RealmList
import java.util.*

class ParentHolderNumeric(
    private val binding: ParentNumricBinding,
    listener: (params: List<Any>) -> Any,
    optionSelector: RealmList<RealmOption>
) : RecyclerView.ViewHolder(binding.root) {

    var adapterListener: (params: List<Any>) -> Any = listener
    var passedSelectedOptions = optionSelector

    fun bind(result: FieledRealm?) {
        selectLabelLanguage(result)
        binding.From.setOnClickListener {
            adapterListener(listOf(result!!.options, "numericFrom"))
        }

        binding.To.setOnClickListener {
            adapterListener(listOf(result!!.options, "numericTo"))
        }


        val fromItem = passedSelectedOptions.find {
            it.field_id == result?.id.toString() && it.whereFrom == "numericFrom"
        }
        fromItem?.let {
            binding.valueFrom.text = it.label_en.toString()
        }

        val toItem = passedSelectedOptions.find {
            it.field_id == result?.id.toString() && it.whereFrom == "numericTo"
        }
        toItem?.let {
            binding.valueTo.text = it.label_en.toString()
        }
    }

    private fun selectLabelLanguage(item: FieledRealm?) {
        if (Locale.getDefault().displayLanguage == "English") {
            binding.parentItemTitle.text = item?.label_en
        } else {
            binding.parentItemTitle.text = item?.label_ar
        }
    }
}
