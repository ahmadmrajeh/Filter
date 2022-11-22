package com.example.filter.ui.screens.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datascource.model.categAndSub.SooqFilterModel
import com.example.datascource.model.options.OptionsResponse
import com.example.datascource.realm.category.CatItemRlm
import com.example.datascource.realm.category.ResultCatRealm
import com.example.datascource.realm.filter.FieledRealm
import com.example.datascource.realm.filter.FilterSubCategory
import com.example.datascource.realm.filter.RealmOption
import com.example.datascource.repository.Repository
 import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    private lateinit var apiDataCategory: RealmList<CatItemRlm>
    lateinit var repository: Repository
    var result: MutableLiveData<ResultCatRealm> = MutableLiveData()
    var resultFilter: MutableLiveData<FilterSubCategory> = MutableLiveData()
    var selectedOptions: MutableLiveData<RealmList<RealmOption>> = MutableLiveData()
    var appLunched = false
    private var childrenWithSelectedParent: ArrayList<String> = ArrayList()

    init {
        selectedOptions.value = RealmList()
    }


    fun setRepository(context: Context) {
        this.repository = Repository(context)
    }



    fun updateOption(
        optionActive: RealmOption,
        selected: Boolean,
        fromWhere: String?
    ) {
        val option = repository.copyRealmOption(optionActive)
        viewModelScope.launch(Dispatchers.IO) {
            option?.let {
                repository.updateOption(it, selected, fromWhere)
                if (it.label_en == "Any") {
                    unSelectOtherOptionsInThisField(it.field_id, fromWhere)
                } else {
                    updateChildOptions(it, selected)
                }

            }
        }
    }


    fun unSelectOtherOptionsInThisField(fieldId: String?, fromWhere: String?) {
        viewModelScope.launch(Dispatchers.Main) {
            val anyList = selectedOptions.value?.filter {
                it.field_id == fieldId
            }

            if (anyList != null) {
                for (unselect in anyList)
                    if (unselect.label_en != "Any") {
                        updateOption(unselect, false, fromWhere)
                        selectedOptions.value?.remove(unselect)
               }
            }
        }
    }


    private fun updateChildOptions(
        option: RealmOption,
        selectedParent: Boolean
    ) { var modifiedFields: ArrayList<String> = ArrayList()
        viewModelScope.launch(Dispatchers.IO) {
            val returnedLists = repository.updateChildOptions(
                option, selectedParent,
                childrenWithSelectedParent, modifiedFields)
            childrenWithSelectedParent = returnedLists[0]
            modifiedFields = returnedLists[1]
            updateOptionsList(modifiedFields)
        }
    }


    private fun updateOptionsList(
        modifiedFields: ArrayList<String>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            for (item in HashSet(modifiedFields)) {
                val data = repository.getField(item)
                data?.let { modifyField(it) }
            }
        }
    }


    private fun modifyField(
        field: FieledRealm
    ) {
        val offlineField = repository.copyRealmField(field)
        viewModelScope.launch(Dispatchers.IO) {
            offlineField?.let { repository.modifyField(it, childrenWithSelectedParent) }
        }
    }



    fun offlineCacheCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val modelItem: SooqFilterModel = repository.catJsonToKotlin()
            apiDataCategory = repository.apiDataCategory(modelItem)
            repository.insertItemToRealm(apiDataCategory)
        }
    }


    fun offlineCacheFilterFields(
        id: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val optionsAndFields: OptionsResponse = repository.optionToKotlin()
            val orderedFields = repository.subFlowJsonToKotlin()
            repository.insertFieldsToRealm(optionsAndFields, orderedFields, id)
        }
    }

    fun readOfflineCacheCategoriesAndSub() {
        viewModelScope.launch(Dispatchers.Main) {
         val data = repository.readOfflineCacheCategoriesAndSub()
         data?.let {
                result.postValue(it)
            }
        }
    }

    fun readOfflineCacheFields(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
             val data = repository.readOfflineCacheFields(id)
            data?.let { param ->
                resultFilter.postValue(param)
            }
        }
    }
}


