package com.example.filter.ui.adapters.sub


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filter.R
import com.example.filter.databinding.SubCategoryBinding
import com.example.datascource.realm.category.SubCatRealm
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter


internal class SubCategoryAdapter(
    data: OrderedRealmCollection<SubCatRealm?>?,
    listener: (id: Int) -> Unit
) :
    RealmRecyclerViewAdapter<SubCatRealm?, RecyclerView.ViewHolder>(data, true) {
    private var TAG = "REALM_RECYCLER_ADAPTER"
    var adapterListener: (id: Int) -> Unit = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.category, parent, false)
        val binding = SubCategoryBinding.bind(view)
        return SubViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = getItem(position)
        Log.i(TAG, "Binding view holder: ${obj?.name}")

        if (obj != null) {
            (holder as SubViewHolder).bind(obj)
        }

        (holder as SubViewHolder).itemView.setOnClickListener {
          adapterListener(obj!!.id)
        }
    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id.toLong()
    }

    init {
        Log.i(TAG, "Created RealmRecyclerViewAdapter for ${getData()!!.size} items.")
    }
}

