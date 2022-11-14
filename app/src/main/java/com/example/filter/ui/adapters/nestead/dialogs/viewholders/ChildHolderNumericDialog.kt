package com.example.filter.ui.adapters.nestead.dialogs.viewholders

 import androidx.recyclerview.widget.RecyclerView
 import com.example.datascource.realm.filter.RealmOption
import com.example.filter.databinding.NumericChildBinding
import io.realm.RealmList
import java.util.*


class ChildHolderNumericDialog(
    private val binding: NumericChildBinding,
    function: (params: List<Any>) -> Unit,
    fromWhere: String,
    passedSelectedOptions: RealmList<RealmOption>
) :
    RecyclerView.ViewHolder(binding.root) {
var buttonChoosed =  fromWhere
var listener =function
    fun bind(item: RealmOption?) {

   binding.root.setOnClickListener {
      listener( listOf(item!!,buttonChoosed )
      ) }
        selectLabelLanguage(item)
   }

    private fun selectLabelLanguage(item:RealmOption?) {
        if (Locale.getDefault().displayLanguage == "English") {
           binding.text.text = item?.label_en
        } else {
            binding.text .text = item?.label
        }
    }
}