package com.example.filter.ui.adapters.nestead.dialogs

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.*
import com.example.filter.ui.adapters.nestead.dialogs.viewholders.ChildHolderIconDialog
import com.example.filter.ui.adapters.nestead.dialogs.viewholders.ChildHolderNumericDialog
import com.example.filter.ui.adapters.nestead.dialogs.viewholders.ChildHolderTextDialog
import com.example.filter.ui.adapters.nestead.parentHolders.ParentHolder
import com.example.filter.ui.adapters.nestead.parentHolders.ParentHolderGrid
import com.example.filter.ui.adapters.nestead.parentHolders.ParentHolderNumeric
import com.example.filter.utils.VIEW_TYPE_GRID
import com.example.filter.utils.VIEW_TYPE_ICON_STRING
import com.example.filter.utils.VIEW_TYPE_LIST_STRING
import com.example.filter.utils.VIEW_TYPE_NUMERIC
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import java.util.ArrayList

internal class AdapterDialog(data: OrderedRealmCollection<RealmOption?>?,comingFrom: String ,listener: (List<Any>) -> Unit ) :
    RealmRecyclerViewAdapter<RealmOption?, RecyclerView.ViewHolder>(data, true) {
    var adapterListener: (List<Any>) -> Unit = listener
    var fromWhere = comingFrom
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {

            VIEW_TYPE_ICON_STRING -> {
                val view = inflater.inflate(R.layout.icon_child, parent, false)
                val binding = IconChildBinding.bind(view)
                ChildHolderIconDialog(binding)
            }

            VIEW_TYPE_NUMERIC -> {

                val view = inflater.inflate(R.layout.numeric_child, parent, false)
                val binding = NumericChildBinding.bind(view)
                ChildHolderNumericDialog(binding)

            }

            else -> {
                val view = inflater.inflate(R.layout.text_grandchild, parent, false)
                val binding = TextGrandchildBinding.bind(view)
                ChildHolderTextDialog(binding)
            }
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = getItem(position)



        when (holder) {
            is ChildHolderNumericDialog -> {
                holder.bind(obj)
            }
            is ChildHolderIconDialog -> {
                holder.bind(obj)
            }
            is ChildHolderTextDialog -> {
                holder.bind(obj)
            }
        }


    }

        override fun getItemId(index: Int): Long {
        return getItem(index)!!.id!!.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return when (fromWhere) {
            "icon" ->  VIEW_TYPE_ICON_STRING
            "numericFrom" -> VIEW_TYPE_NUMERIC
            "numericTo" -> VIEW_TYPE_NUMERIC
            else -> VIEW_TYPE_LIST_STRING
        }
    }
}