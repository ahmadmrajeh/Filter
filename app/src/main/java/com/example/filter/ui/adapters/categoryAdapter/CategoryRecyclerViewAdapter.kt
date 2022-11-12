package com.example.filter.ui.adapters.categoryAdapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.category.CatItemRlm
import com.example.filter.R
import com.example.filter.databinding.CategoryBinding
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter


internal class CategoryRecyclerViewAdapter(
    data: OrderedRealmCollection< CatItemRlm?>?,
    listener: (id: Int) -> Unit
) :
    RealmRecyclerViewAdapter< CatItemRlm?, RecyclerView.ViewHolder>(data, true) {
    private var TAG = "REALM_RECYCLER_ADAPTER"
    var adapterListener: (id: Int) -> Unit = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.category, parent, false)
        val binding = CategoryBinding.bind(view)
        return CategoryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = getItem(position)
        Log.i(TAG, "Binding view holder: ${obj?.name}")

        if (obj != null) {
            (holder as CategoryViewHolder).bind(obj)
        }

        (holder as CategoryViewHolder).itemView.setOnClickListener {
            adapterListener(position)
        }
    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id.toLong()
    }

    init {
        Log.i(TAG, "Created RealmRecyclerViewAdapter for ${getData()!!.size} items.")
    }
}


