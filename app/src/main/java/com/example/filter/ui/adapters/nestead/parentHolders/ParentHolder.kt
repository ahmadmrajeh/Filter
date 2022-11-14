package com.example.filter.ui.adapters.nestead.parentHolders

 import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datascource.realm.filter.FieledRealm
 import com.example.datascource.realm.filter.RealmOption
 import com.example.filter.databinding.ParentItemBinding
import com.example.filter.ui.adapters.nestead.childs.ChildMembersAdapter
 import io.realm.RealmList
 import java.util.*

class  ParentHolder(
    private val binding: ParentItemBinding,
    listener: (params: List<Any>) -> Unit,
    listener2: (params: List<Any>) -> Unit,
    listener3: (params: List<Any>) -> Unit,
    listener4: (params: List<Any>) -> Unit,
    viewType: Int,
    passedSelectedOptions: RealmList<RealmOption>
) : RecyclerView.ViewHolder(binding.root){
    var viewTypeTextOrImg = viewType
    var adapterListener: (params: List<Any>) -> Unit = listener
    var clickListenerImg: (params: List<Any>) -> Unit =  listener2
    var adapterListenerDialog: (params: List<Any>) -> Unit = listener3
    var clickListenerImgDialog: (params: List<Any>) -> Unit =  listener4
    var type = viewType
    var realmLiveOptions =passedSelectedOptions

    fun bind(result: FieledRealm?) {

        selectLabelLanguage(result )
        circleMembersAdapter(result)
        binding.selectedClicked.setOnClickListener {
        if (type== 3) {
            clickListenerImgDialog(listOf(result!!.options,"icon") )
        } else {
            adapterListenerDialog(listOf(result!!.options,"string") )
        }
    }
 }


    private fun circleMembersAdapter(result: FieledRealm?) {
        val childMembersAdapter = ChildMembersAdapter(
            result?.options, adapterListener, viewTypeTextOrImg, clickListenerImg
       ,realmLiveOptions )
        binding.childRecyclerview.layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.HORIZONTAL, false
        )
        binding.childRecyclerview.adapter = childMembersAdapter
    }


    private fun selectLabelLanguage(item:FieledRealm?) {
        if (Locale.getDefault().displayLanguage == "English") {
            binding.parentItemTitle.text = item?.label_en
        } else {
            binding.parentItemTitle.text = item?.label_ar
        }
    }
}