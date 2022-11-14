package com.example.filter.ui.adapters.nestead.childs.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.ChildItemBinding
import io.realm.RealmList


class ChildHolderCircle(
    private val binding: ChildItemBinding,
    clickListenerImg: (params: List<Any>) -> Unit,
    passedSelectedOptions: RealmList<RealmOption>
) :
    RecyclerView.ViewHolder(binding.root) {
 var listener  = clickListenerImg
    var selected=   passedSelectedOptions
    fun bind(item: RealmOption?) {

        if (item?.parent_id ==null || item in selected.filter {
                it.id == item.parent_id
            }) {


            if (item!!.isSelected) inSelectedItems()
            else  notInSelectedItems()


            binding.root.visibility = View.VISIBLE
            Glide.with(itemView)
                .load(item?.option_img)
                .placeholder(R.drawable.ic_launcher_foreground)
                .circleCrop()
                .into(binding.icon)

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
}