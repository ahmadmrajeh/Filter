package com.example.filter.ui.adapters.nestead.childs

import android.util.Log
import android.view.LayoutInflater
 import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
 import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.ChildItemBinding
import com.example.filter.databinding.ChildItemTextCircleBinding
import com.example.filter.ui.adapters.nestead.ParentAdapter
import com.example.filter.ui.adapters.nestead.childs.viewholders.*
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter


internal class ChildMembersAdapter(
    data: OrderedRealmCollection<RealmOption?>?,
    listener: (id: Int) -> Unit,
    viewType: Int,
    listener2: (id: Int) -> Unit
) :
    RealmRecyclerViewAdapter<RealmOption?, RecyclerView.ViewHolder>(data, true) {

    var  adapterListener: (id: Int) -> Unit = listener
    var  clickListenerImg: (id: Int) -> Unit = listener2
    var  viewTypeTextOrImg= viewType
    override fun getItemViewType(position: Int): Int {
        val obj = getItem(position)
        return when (obj?.option_img) {

            null ->  ParentAdapter.VIEW_TYPE_LIST_STRING

            else -> {
                ParentAdapter.VIEW_TYPE_ICON_STRING
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewTypeTextOrImg) {

            3  -> {
          val returned =  if (viewType != 3) {
              val view = inflater.inflate(R.layout.child_item_text_circle, parent, false)
              val binding = ChildItemTextCircleBinding.bind(view)
              ChildHolderTextCircle(binding,adapterListener)
                } else {
              val view = inflater.inflate(R.layout.child_item, parent, false)
              val binding = ChildItemBinding.bind(view)
              ChildHolderCircle(binding,clickListenerImg)


                }
                returned
            }

            else -> {
                val view = inflater.inflate(R.layout.child_item_text_circle, parent, false)
                val binding = ChildItemTextCircleBinding.bind(view)
                ChildHolderTextCircle(binding,adapterListener)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val obj = getItem(position)
        Log.i("Binding", "Binding view holder: ${obj?.label}")


        when (holder) {
            is ChildHolderCircle -> {
                holder.bind(obj)
            }
            is ChildHolderTextCircle -> {
                holder.bind(obj)
            }

        }



    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id!!.toLong()
    }

}
