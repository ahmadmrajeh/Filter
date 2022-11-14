package com.example.filter.ui.adapters.nestead.dialogs.viewholders


import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.IconChildBinding
import io.realm.RealmList
import java.util.*


class ChildHolderIconDialog(
    private val binding: IconChildBinding,
    clickListenerImg: (params: List<Any>) -> Unit,
    passedSelectedOptions: RealmList<RealmOption>
) :
    RecyclerView.ViewHolder(binding.root) {
    var listener  = clickListenerImg
    var selected=   passedSelectedOptions

    fun bind(item: RealmOption?) {

   binding.root.setOnClickListener{

    }
                Glide.with(itemView)
            .load( item?.option_img)
            .placeholder(R.drawable.ic_any_check_24)
            .circleCrop()
            .into(binding.iconImg)
        selectLabelLanguage(item)

        binding.checkBox.setOnClickListener {

        }
        if (item?.parent_id == null || item in selected.filter {
                it.id.toString() == item.parent_id
            }) {

            binding.checkBox.isChecked = item!!.isSelected

            binding.root.visibility = View.VISIBLE
            binding.checkBox.setOnClickListener {
                handleClick(item)
            }
        } else {
            binding.root.visibility = View.GONE
        }
    }

    private fun handleClick(item: RealmOption?) {
        if (item!!.isSelected) {
            binding.checkBox.isChecked = false
            listener(listOf(item, "horizontal" ,false))
        } else {
            binding.checkBox.isChecked = true
            listener(listOf(item,"horizontal" ,true))
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