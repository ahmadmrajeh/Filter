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
    clickListenerImg: (option: RealmOption, isSelected:Boolean) -> Unit,
    passedSelectedOptions: RealmList<RealmOption>
) :
    RecyclerView.ViewHolder(binding.root) {
    var listener  = clickListenerImg
    var selected=   passedSelectedOptions

    fun bind(item: RealmOption?) {


                Glide.with(itemView)
            .load( item?.option_img)
            .placeholder(R.drawable.ic_any_check_24)
            .circleCrop()
            .into(binding.iconImg)
        selectLabelLanguage(item)

        if (item != null) {
            binding.checkBox.isChecked = item.isSelected
        }

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
                listener(item,true)
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