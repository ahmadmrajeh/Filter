package com.example.filter.ui.adapters.nestead.childs

import android.util.Log
import android.view.LayoutInflater
 import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
 import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.ChildItemBinding
 import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter


internal class ChildMembersAdapter(data: OrderedRealmCollection<RealmOption?>?, listener:   (id: Int) -> Unit ) :
    RealmRecyclerViewAdapter<RealmOption?, RecyclerView.ViewHolder>(data, true) {

    var adapterListener: (id: Int) -> Unit = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.child_item, parent, false)
        val binding = ChildItemBinding.bind(view)
        return ChildHolderOne(binding,parent.context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = getItem(position)
        Log.i("TAG", "Binding view holder: ${obj?.label}")

        (holder as ChildHolderOne).bind(obj)

        (holder as ChildHolderOne).itemView.setOnClickListener{
            adapterListener(position)
        }
    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id!!.toLong()
    }

}
