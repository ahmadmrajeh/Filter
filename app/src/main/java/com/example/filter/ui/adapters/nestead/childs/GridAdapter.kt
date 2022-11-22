package com.example.filter.ui.adapters.nestead.childs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.ChildGridBinding
import com.example.filter.ui.adapters.nestead.childs.viewholders.ChildHolderGrid
import io.realm.OrderedRealmCollection
import io.realm.RealmList
import io.realm.RealmRecyclerViewAdapter

internal class GridAdapter(
    data: OrderedRealmCollection<RealmOption?>?,
    listener: (option: RealmOption, isSelected: Boolean) -> Unit,
    realmLiveOptions: RealmList<RealmOption>
) :
    RealmRecyclerViewAdapter<RealmOption?, RecyclerView.ViewHolder>(data, true) {
    var adapterListener = listener
    private var selectedFromLive = realmLiveOptions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.child_grid, parent, false)
        val binding = ChildGridBinding.bind(view)
        return ChildHolderGrid(binding, adapterListener, selectedFromLive)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = getItem(position)
        obj?.let { (holder as ChildHolderGrid).bind(it) }

    }

    override fun getItemId(index: Int): Long {
        return getItem(index)?.id?.toLong()?:0L
    }

}