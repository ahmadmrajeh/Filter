package com.example.filter.ui.adapters.nestead.dialogs

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.NumericChildBinding
import com.example.filter.ui.adapters.nestead.dialogs.viewholders.ChildHolderNumericDialog
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

internal class AdapterDialog(data: OrderedRealmCollection<RealmOption?>?, listener:   (id: Int) -> Unit ) :
    RealmRecyclerViewAdapter<RealmOption?, RecyclerView.ViewHolder>(data, true) {


    var adapterListener: (id: Int) -> Unit = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater
            .inflate(R.layout.numeric_child, parent, false)
        val binding = NumericChildBinding.bind(view)
        return ChildHolderNumericDialog(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val obj = getItem(position)
        Log.i("Binding", "Binding view holder: ${obj?.label}")


        (holder as ChildHolderNumericDialog).bind(obj)

        /*      (holder as ChildHolderGrid).itemView.setOnClickListener{
                  adapterListener(position)
              }*/


    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id!!.toLong()
    }

}