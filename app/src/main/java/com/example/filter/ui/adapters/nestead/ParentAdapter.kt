package com.example.filter.ui.adapters.nestead

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.GridParentBinding
import com.example.filter.databinding.ParentItemBinding
import com.example.filter.databinding.ParentNumricBinding
import com.example.filter.ui.adapters.nestead.parentHolders.ParentHolder
import com.example.filter.ui.adapters.nestead.parentHolders.ParentHolderGrid
import com.example.filter.ui.adapters.nestead.parentHolders.ParentHolderNumeric
import com.example.filter.utils.VIEW_TYPE_GRID
import com.example.filter.utils.VIEW_TYPE_ICON_STRING
import com.example.filter.utils.VIEW_TYPE_LIST_STRING
import com.example.filter.utils.VIEW_TYPE_NUMERIC
import io.realm.OrderedRealmCollection
import io.realm.RealmList
import io.realm.RealmRecyclerViewAdapter

internal class ParentAdapter(
    data: OrderedRealmCollection<FieledRealm?>?,
    listener: List<(params: List<Any>) -> Any>,
    realmLiveOptions: RealmList<RealmOption>
) :
    RealmRecyclerViewAdapter<FieledRealm?, RecyclerView.ViewHolder>(data, true) {
    var listOfListeners: List<(params: List<Any>) -> Any> = listener
    var passedSelectedOptions = realmLiveOptions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return viewHolderChooser(viewType, inflater, parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = getItem(position)

        when (holder) {
            is ParentHolder -> {


                holder.bind(obj!!)

            }
            is ParentHolderNumeric -> {
                holder.bind(obj)
            }
            is ParentHolderGrid -> {
                holder.bind(obj!!)
            }
        }
    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id!!.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        val obj = getItem(position)
        return when (obj?.data_type) {

            "list_string_boolean" -> VIEW_TYPE_GRID
            "list_string_icon" -> VIEW_TYPE_ICON_STRING
            "list_numeric" -> VIEW_TYPE_NUMERIC
            else -> VIEW_TYPE_LIST_STRING
        }
    }


    private fun viewHolderChooser(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = when (viewType) {
        VIEW_TYPE_GRID -> {
            val view = inflater.inflate(R.layout.grid_parent, parent, false)
            val binding = GridParentBinding.bind(view)
            ParentHolderGrid(binding, listOfListeners[0], passedSelectedOptions)
        }

        VIEW_TYPE_NUMERIC -> {
            val view = inflater.inflate(R.layout.parent_numric, parent, false)
            val binding = ParentNumricBinding.bind(view)
            ParentHolderNumeric(binding, listOfListeners[1], passedSelectedOptions)
        }

        else -> {
            val view = inflater.inflate(R.layout.parent_item, parent, false)
            val binding = ParentItemBinding.bind(view)
            ParentHolder(
                binding,
                listOfListeners[2],
                listOfListeners[3],
                listOfListeners[4],
                listOfListeners[5],
                listOfListeners[6],
                viewType,
                passedSelectedOptions
            )
        }
    }
}

