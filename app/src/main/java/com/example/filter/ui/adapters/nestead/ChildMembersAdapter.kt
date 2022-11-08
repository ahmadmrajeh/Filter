package com.example.filter.ui.adapters.nestead

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filter.R
import com.example.filter.realm.filter.FieledRealm
/*
open class ChildMembersAdapter(private var fieldData: List<FieledRealm>) :
    RecyclerView.Adapter<ChildMembersAdapter.DataViewHolder>() {

    private var membersList: List<FieledRealm> = ArrayList()

    init {
        this.membersList = fieldData
    }

    var onItemClick: ((String) -> Unit)? = null



    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(membersList[adapterPosition].name)
            }
        }

        fun bind(result:FieledRealm) {
           // itemView.name.text = result.name

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_row_child, parent,
            false
        )

    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(membersList[position])

    }

    override fun getItemCount(): Int = membersList.size


}
*/