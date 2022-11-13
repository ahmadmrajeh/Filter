package com.example.filter.ui.adapters.nestead.childs.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.ChildItemTextCircleBinding
import java.util.*


class ChildHolderTextCircle(
    private val binding: ChildItemTextCircleBinding,
    adapterListener: (params: List<Any>) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(item: RealmOption?) {
        selectLabelLanguage(item)
        binding.root.setOnClickListener{
            selectionState()
        }
    }

    private fun selectionState() {
        if (binding.ticked.visibility == View.INVISIBLE) {
            binding.constraint.background = ContextCompat.getDrawable(
                itemView.context, R.drawable.circle_selected_option_bg
            )
            binding.ticked.visibility = View.VISIBLE
        } else {
            binding.constraint.background = ContextCompat.getDrawable(
                itemView.context, R.drawable.circle_option_bg
            )
            binding.ticked.visibility = View.INVISIBLE
        }
    }

    private fun selectLabelLanguage(item:RealmOption?) {
        if (Locale.getDefault().displayLanguage == "English") {
            binding.stringCircle .text = item?.label_en
        } else {
            binding.stringCircle.text = item?.label
        }
    }

}