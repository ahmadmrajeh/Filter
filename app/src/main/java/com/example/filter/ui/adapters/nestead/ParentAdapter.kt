package com.example.filter.ui.adapters.nestead

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
import com.example.filter.R
import com.example.filter.databinding.ChildItemBinding
import com.example.filter.databinding.ParentItemBinding
import com.example.filter.ui.adapters.nestead.childs.ChildHolderOne
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter


internal class ParentAdapter (data: OrderedRealmCollection<FieledRealm?>?, listener:   (id: Int) -> Unit ) :
    RealmRecyclerViewAdapter<FieledRealm?, RecyclerView.ViewHolder>(data, true) {

    var adapterListener: (id: Int) -> Unit = listener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.parent_item, parent, false)
        val binding = ParentItemBinding.bind(view)
        return ParentHolder(binding,adapterListener)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = getItem(position)
        Log.i("TAG", "Binding view holder: ${obj?.name}")

        (holder as ParentHolder).bind(obj)


        /*     (holder as ChildHolderOne).itemView.setOnClickListener{
                 adapterListener(position)
             }*/

    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id!!.toLong()
    }



}