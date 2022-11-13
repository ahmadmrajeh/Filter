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

    fun bind(item: RealmOption?) {
        Glide.with(itemView)
            .load( item?.option_img)
            .placeholder(R.drawable.ic_launcher_foreground)
            .circleCrop()
            .into(binding.icon)

        binding.root.setOnClickListener {
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
                itemView.context, R.drawable.circle_option_bg)
            binding.ticked.visibility = View.INVISIBLE
        }
    }
}