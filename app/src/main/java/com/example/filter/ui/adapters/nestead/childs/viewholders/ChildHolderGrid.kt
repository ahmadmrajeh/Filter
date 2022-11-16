package com.example.filter.ui.adapters.nestead.childs.viewholders

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.ChildGridBinding
import io.realm.RealmList
import java.util.*
import kotlin.collections.ArrayList


class ChildHolderGrid(
    private val binding: ChildGridBinding,
    adapterListener: (params: List<Any>) -> Any,
    selectedFromLive: RealmList<RealmOption>
) : RecyclerView.ViewHolder(binding.root) {
    var listener = adapterListener
    var selected = selectedFromLive

    fun bind(item: RealmOption) {
        selectLabelLanguage(item)


        if (item in selected.filter {
                item.parent_id == it?.id
            } || item.parent_id == null) {

            binding.root.visibility = View.VISIBLE
            if (item.isSelected) inSelectedItems()
            else notInSelectedItems()
            binding.root.setOnClickListener {
                handleClick(item)
            }
        } else {
            binding.root.visibility = View.GONE
        }
    }

    private fun handleClick(item: RealmOption?) {
        if (item!!.isSelected
        ) {
            notInSelectedItems()
            listener(listOf(item,  false))

        } else {
            inSelectedItems()
            listener(listOf(item,  true))
        }
    }

    private fun notInSelectedItems() {
        binding.root.setBackgroundColor(Color.parseColor("#00000000"))
    }

    private fun inSelectedItems() {
        binding.root.setBackgroundColor(Color.parseColor("#578AE1"))
    }

    private fun selectLabelLanguage(item: RealmOption?) {
        if (Locale.getDefault().displayLanguage == "English") {
            binding.gridElement.text = item?.label_en
        } else {
            binding.gridElement.text = item?.label
        }
    }
}