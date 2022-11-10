package com.example.filter.ui.adapters.nestead.childs.viewholders

import android.content.Context
import android.graphics.Color
  import androidx.recyclerview.widget.RecyclerView
 import com.example.datascource.realm.filter.RealmOption
 import com.example.filter.databinding.ChildGridBinding
import java.util.*


class ChildHolderGrid(private val binding: ChildGridBinding ) :
    RecyclerView.ViewHolder(binding.root) {




    fun bind(item: RealmOption?) {

binding.root.setOnClickListener{
    selectionState()
}
        selectLabelLanguage(item)
    }

    private fun selectionState() {
        if (binding.root.tag == "1") {
            binding.root.setBackgroundColor(Color.parseColor("#578AE1"))
            binding.root.tag = "0"
        } else {
            binding.root.setBackgroundColor(Color.parseColor("#00000000"))
            binding.root.tag = "1"

        }
    }

    private fun selectLabelLanguage(item:RealmOption?) {
        if (Locale.getDefault().displayLanguage == "English") {
            binding.textView4.text = item?.label_en
        } else {
            binding.textView4.text = item?.label
        }
    }



}