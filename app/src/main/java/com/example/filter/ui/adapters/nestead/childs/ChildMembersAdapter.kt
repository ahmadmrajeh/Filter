package com.example.filter.ui.adapters.nestead.childs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.ChildItemBinding
import com.example.filter.databinding.ChildItemTextCircleBinding
import com.example.filter.ui.adapters.nestead.childs.viewholders.*
import com.example.filter.utils.VIEW_TYPE_ICON_STRING
import com.example.filter.utils.VIEW_TYPE_LIST_STRING
import io.realm.OrderedRealmCollection
import io.realm.RealmList
import io.realm.RealmRecyclerViewAdapter


internal class ChildMembersAdapter(
    data: OrderedRealmCollection<RealmOption?>?,
    listener: (option: RealmOption, isSelected:Boolean) -> Unit,
    viewType: Int,
    listener2: (option: RealmOption, isSelected:Boolean)-> Unit,
    realmLiveOptions: RealmList<RealmOption>
) :
    RealmRecyclerViewAdapter<RealmOption?, RecyclerView.ViewHolder>(data, true) {
    var passedSelectedOptions = realmLiveOptions
    var adapterListener  = listener
    var clickListenerImg = listener2
    private var viewTypeTextOrImg = viewType


    override fun getItemViewType(position: Int): Int {
        val obj = getItem(position)
        return when (obj?.option_img) {
            null -> VIEW_TYPE_LIST_STRING
            else -> VIEW_TYPE_ICON_STRING
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return viewHolder(viewType, inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = getItem(position)
        when (holder) {
            is ChildHolderCircle -> {
                obj?.let { holder.bind(it) }
            }
            is ChildHolderTextCircle -> {
                obj?.let { holder.bind(it) }
            }
        }
    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id!!.toLong()
    }

    private fun childHolderCircle(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ChildHolderCircle {
        val view = inflater.inflate(R.layout.child_item, parent, false)
        val binding = ChildItemBinding.bind(view)
        return ChildHolderCircle(
            binding,
            clickListenerImg, passedSelectedOptions
        )
    }

    private fun childHolderTextCircle(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ChildHolderTextCircle {
        val view = inflater.inflate(R.layout.child_item_text_circle, parent, false)
        val binding = ChildItemTextCircleBinding.bind(view)
        return ChildHolderTextCircle(
            binding,
            adapterListener, passedSelectedOptions
        )
    }

    private fun viewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = when (viewTypeTextOrImg) {

        VIEW_TYPE_ICON_STRING -> {
            val returned = if (viewType != VIEW_TYPE_ICON_STRING) {
                childHolderTextCircle(inflater, parent)
            } else {
                childHolderCircle(inflater, parent)
            }
            returned
        }

        else -> {
            childHolderTextCircle(inflater, parent)
        }
    }
}
