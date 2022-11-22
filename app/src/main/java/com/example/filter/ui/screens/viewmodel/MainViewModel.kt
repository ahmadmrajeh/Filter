package com.example.filter.ui.screens.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datascource.model.options.OptionsResponse
import com.example.datascource.model.searchRes.SearchRes
import com.example.datascource.realm.category.CatItemRlm
import com.example.datascource.realm.category.ResultCatRealm
import com.example.datascource.realm.filter.FieledRealm
import com.example.datascource.realm.filter.FilterSubCategory
import com.example.datascource.realm.filter.RealmOption
import com.example.datascource.repository.Repository
import com.example.filter.utils.JsonMockApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


    class MainViewModel : ViewModel() {
     private lateinit var apiDataCategory: RealmList<CatItemRlm>
    var repository = Repository()
    var result: MutableLiveData<ResultCatRealm> = MutableLiveData()
    var resultFilter: MutableLiveData<FilterSubCategory> = MutableLiveData()
    var selectedOptions: MutableLiveData<RealmList<RealmOption>> = MutableLiveData()
     var appLunched = false
    private var childrenWithSelectedParent: ArrayList<String> = ArrayList()


        private fun optionJsonToKotlin(applicationContext: Context, orderedFields: SearchRes, id: Int) {
        val jsonFileString = JsonMockApi.getJsonDataFromAsset(
            applicationContext, "dynamic.json"
        )

        val gson = Gson()
        val type = object : TypeToken<OptionsResponse>() {}.type

        val optionsAndFields: OptionsResponse =
            gson.fromJson(jsonFileString, type)
        offlineCacheFilterFields(optionsAndFields, orderedFields, id)
    }

    fun subFlowJsonToKotlin(applicationContext: Context, id: Int) {
        val jsonFileString = JsonMockApi.getJsonDataFromAsset(
            applicationContext,
            "assign.json"
        )

        val gson = Gson()
        val type = object : TypeToken<SearchRes>() {}.type
        val passedToOptions: SearchRes = gson.fromJson(jsonFileString, type)
        optionJsonToKotlin(applicationContext, passedToOptions, id)
    }


    fun catJsonToKotlin(applicationContext: Context) {
        val jsonFileString = JsonMockApi.getJsonDataFromAsset(
            applicationContext,
            "categories.json"
        )

        val gson = Gson()
        val type = object : TypeToken<com.example.datascource.model.
        categAndSub.SooqFilterModel>() {}.type
        offlineCacheCategories(gson.fromJson(jsonFileString, type))
    }


    private fun offlineCacheCategories(
        modelItem: com.example.datascource.model
        .categAndSub.SooqFilterModel
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            apiDataCategory = repository.apiDataCategory(modelItem)
            val realmWrite = Realm.getDefaultInstance()
            repository.insertItemToRealm(apiDataCategory, realmWrite)
        }
    }


    private fun offlineCacheFilterFields(
        optionsAndFields: OptionsResponse,
        orderedFields: SearchRes,
        id: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val realmWrite = Realm.getDefaultInstance()
            repository.insertFieldsToRealm(optionsAndFields, orderedFields, realmWrite, id)
        }
    }

    fun updateOption(
        optionActive: RealmOption,
        selected: Boolean,
        fromWhere: String?
    ) {
        val realmWrite = Realm.getDefaultInstance()
        val option = realmWrite.copyFromRealm(optionActive)
        realmWrite.close()



        viewModelScope.launch(Dispatchers.IO) {
        val  db = Realm.getDefaultInstance()
            db.executeTransactionAwait(Dispatchers.IO) {
                val newOption = RealmOption().apply {
                    field_id = option.field_id
                    has_child = option.has_child
                    id = option.id
                    label = option.label
                    label_en = option.label_en
                    option_img = option.option_img
                    order = option.order
                    parent_id = option.parent_id
                    value = option.value
                    isSelected = selected
                    whereFrom = fromWhere
                    parentIsSelected = false
                }
                it.insertOrUpdate(newOption)
            }
            if (option.label_en == "Any")
            {  unSelectOtherOptionsInThisField(option.field_id,fromWhere)

            } else
           updateChildOptions(option, selected)
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
        ) {

            val modifiedFields: ArrayList<String> = ArrayList()
            viewModelScope.launch(Dispatchers.IO) {
                val realmWrite = Realm.getDefaultInstance()
                realmWrite.executeTransactionAwait(Dispatchers.IO) {
                    val data = realmWrite.where(RealmOption::class.java)?.equalTo("parent_id", option.id)
                            ?.findAll()


                    //this option is the parent of the elements in data
                    if (data != null) {
                        for (item in data) {

                            item.id?.let { itemId ->
                            if (selectedParent)
                                childrenWithSelectedParent.add(itemId)
                            else childrenWithSelectedParent.remove(itemId)
                            }

                            item.field_id?.let { it1 -> modifiedFields.add(it1) }
                            val newOption = RealmOption().apply {
                                field_id = item.field_id
                                has_child = item.has_child
                                id = item.id
                                label = item.label
                                label_en = item.label_en
                                option_img = item.option_img
                                order = item.order
                                parent_id = item.parent_id
                                value = item.value
                                isSelected = item.isSelected
                                whereFrom = item.whereFrom
                                parentIsSelected = selectedParent
                            }
                            it.insertOrUpdate(newOption)
                        }
                    }
                    updateOptionsList(modifiedFields)
                }
            }

        }





        private fun updateOptionsList(
        modifiedFields: ArrayList<String>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val realmWrite = Realm.getDefaultInstance()

            realmWrite.executeTransactionAwait(Dispatchers.IO) { realm ->
                for (item in HashSet(modifiedFields)) {

                    val data =
                        realmWrite.where(FieledRealm::class.java)?.equalTo("id", item.toInt())
                            ?.findFirst()
                    data?.let { modifyField(it) }
                }
            }
        }
    }


    private fun modifyField(
        field: FieledRealm
    ) {
        val realmWrite = Realm.getDefaultInstance()
      val  offlineField= realmWrite.copyFromRealm(field)
        realmWrite.close()
        viewModelScope.launch(Dispatchers.IO) {
     val   realmDb = Realm.getDefaultInstance()

            realmDb.executeTransactionAwait(Dispatchers.IO) { realm ->
                val tempList: RealmList<RealmOption> = RealmList()

                    for (item in offlineField.optionsResistance) {
                        if ( item.id in  childrenWithSelectedParent|| item.parent_id == null)
                            tempList.add(item)
                    }

                val newOption = FieledRealm().apply {
                    data_type = offlineField.data_type
                    id = offlineField.id
                    name = offlineField.name
                    parent_id = offlineField.parent_id
                    parent_name = offlineField.parent_name
                    label_ar = offlineField.label_ar
                    label_en = offlineField.label_en
                    options = tempList
                    optionsResistance = offlineField.optionsResistance
                }
                realm.insertOrUpdate(newOption)
            }
        }
    }


    fun readOfflineCacheCategoriesAndSub() {
        viewModelScope.launch(Dispatchers.Main) {
            val db: Realm = Realm.getDefaultInstance()
            val data = db.where(ResultCatRealm::class.java)?.findFirst()
            data?.let {
                result.postValue(it)
            }
        }
    }

    fun readOfflineCacheFields(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val db: Realm = Realm.getDefaultInstance()
            val data = db.where(FilterSubCategory::class.java)
                .equalTo("idSubCategory", id)?.findFirst()
            data?.let { param ->
                resultFilter.postValue(param)

            }

        }
    }




    }


