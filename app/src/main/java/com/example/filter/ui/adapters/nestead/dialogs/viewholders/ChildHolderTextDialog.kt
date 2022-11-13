package com.example.filter.ui.adapters.nestead.dialogs.viewholders


import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.databinding.TextGrandchildBinding
import java.util.*


class ChildHolderTextDialog(private val binding: TextGrandchildBinding ) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(item: RealmOption?) {

   binding.root.setOnClickListener{

    }

        selectLabelLanguage(item)

    }

    private fun selectLabelLanguage(item:RealmOption?) {

        if (Locale.getDefault().displayLanguage == "English") {
           binding.text.text = item?.label_en
        } else {
            binding.text .text = item?.label
        }
    }

}