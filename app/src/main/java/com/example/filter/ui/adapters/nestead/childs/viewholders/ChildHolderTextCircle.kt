package com.example.filter.ui.adapters.nestead.childs.viewholders

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.ChildItemTextCircleBinding
import io.realm.RealmList
import java.util.*


class ChildHolderTextCircle(
    private val binding: ChildItemTextCircleBinding ,
    adapterListener: (params: List<Any>) -> Unit ,
    passedSelectedOptions: RealmList<RealmOption>
) :
    RecyclerView.ViewHolder(binding.root) {
    var listener  = adapterListener
    var selected=   passedSelectedOptions
    fun bind(item: RealmOption?) {
        Log.e("checker",  item?. parent_id.toString())
        Log.e("LIV1212", "passed $selected")
        if ( item in selected.filter {
                item?.parent_id ==  it.id
            } ||  item?.parent_id == null)  {




            binding.root.visibility = View.VISIBLE
            if (item!!.isSelected) inSelectedItems()
            else  notInSelectedItems()

            selectLabelLanguage(item)
            binding.root.setOnClickListener {
               //  selectionState()
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
            listener(listOf(item, "horizontal" ,false))

        } else {
            inSelectedItems()
            listener(listOf(item,"horizontal" ,true))
        }
    }



    private fun notInSelectedItems() {
        binding.constraint.background = ContextCompat.getDrawable(
            itemView.context, R.drawable.circle_option_bg
        )
        binding.ticked.visibility = View.INVISIBLE
    }

    private fun inSelectedItems() {
        binding.constraint.background = ContextCompat.getDrawable(
            itemView.context, R.drawable.circle_selected_option_bg
        )
        binding.ticked.visibility = View.VISIBLE
    }

    private fun selectLabelLanguage(item:RealmOption?) {
        if (Locale.getDefault().displayLanguage == "English") {
            binding.stringCircle .text = item?.label_en
        } else {
            binding.stringCircle.text = item?.label
        }
    }

}