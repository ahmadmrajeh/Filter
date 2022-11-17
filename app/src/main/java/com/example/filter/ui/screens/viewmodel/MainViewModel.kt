package com.example.filter.ui.screens.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datascource.model.options.OptionsResponse
import com.example.datascource.model.searchRes.SearchRes
import com.example.datascource.realm.AppModules
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
import io.realm.RealmConfiguration
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    var copyOfDaFieldsData: MutableLiveData<FilterSubCategory> = MutableLiveData()
    private lateinit var apiDataCategory: RealmList<CatItemRlm>
    var repository = Repository()
    var result: MutableLiveData<ResultCatRealm> = MutableLiveData()
    var resultFilter: MutableLiveData<FilterSubCategory> = MutableLiveData()
    var selectedOptions: MutableLiveData<RealmList<RealmOption>> = MutableLiveData()
    private var timesClicked: Int = 1
    var timesExecuted: Int = 0
    private val fieldOriginalData = HashMap<String, FieledRealm>()

    private var config = RealmConfiguration.Builder().schemaVersion(7).modules(
        AppModules()
    )
        .deleteRealmIfMigrationNeeded()
        .name("realm.db")
        .allowQueriesOnUiThread(true)
        .allowWritesOnUiThread(true)
        .build()


    private   var childrenWithSelectedParent: RealmList<RealmOption>  = RealmList()

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
            val realmWrite = Realm.getDefaultInstance( )
            repository.insertItemToRealm(apiDataCategory, realmWrite)
        }
    }


    private fun offlineCacheFilterFields(
        optionsAndFields: OptionsResponse,
        orderedFields: SearchRes,
        id: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val realmWrite = Realm.getDefaultInstance( )
            repository.insertFieldsToRealm(optionsAndFields, orderedFields, realmWrite, id)
        }
    }


    fun updateOption(
        option: RealmOption,
        selected: Boolean,
        fromWhere: String?
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            var realmWrite = Realm.getDefaultInstance()

            realmWrite.executeTransaction {

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
        }
        updateChildOptions(option, selected)

    }

    private fun updateChildOptions(
        option: RealmOption,
        selectedParent: Boolean,
    ) {



        viewModelScope.launch(Dispatchers.IO) {
            var realmWrite = Realm.getDefaultInstance()
            realmWrite.executeTransaction {

                val data = realmWrite.where(RealmOption::class.java)?.equalTo("parent_id", option.id)?.findAll()
                //this option is the parent of the elements in data
                if (data != null) {
                    for (item in data) {
                        if (selectedParent) childrenWithSelectedParent.add(item)
                        else childrenWithSelectedParent.remove(item)
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
                    Log.e(
                        "numerictraces",
                        childrenWithSelectedParent.toString()
                    )
                    timesClicked += 1
                }
            }
        }

    }

    fun updateOptionsList(
        field: FieledRealm
    ) {
        if (timesClicked > timesExecuted)  {

        viewModelScope.launch(Dispatchers.IO) {
            val realmWrite = Realm.getDefaultInstance()
            val offlineField = realmWrite.copyFromRealm(field)
            realmWrite.executeTransaction { realm ->

                if (fieldOriginalData[offlineField.id.toString()]?.name.isNullOrEmpty()) {
                    fieldOriginalData[offlineField.id.toString()] = offlineField
                    /*Log.e(
                        "numerictrace",
                        offlineField.id.toString() + "  " + fieldOriginalData[offlineField.id.toString()]?.options.toString()
                    )*/
                }

                val tempList: RealmList<RealmOption> = RealmList()


                fieldOriginalData[offlineField.id.toString()]?.let {
                    for (item in it.options) {

                        if (! childrenWithSelectedParent.isEmpty()){
                            Log.e(
                                "numerictraces",
                                childrenWithSelectedParent.toString()
                            )
                        val existed = childrenWithSelectedParent.find { i ->
                            item.id == i.id
                        }
                        if (!existed?.id.isNullOrEmpty())
                            tempList.add(item)
                    }

                        if (  item.parent_id == null)
                            tempList.add(item)

                    }
                }


                if (tempList != offlineField.options) {
                    val newOption = FieledRealm().apply {
                        data_type = offlineField.data_type
                        id = offlineField.id
                        name = offlineField.name
                        parent_id = offlineField.parent_id
                        parent_name = offlineField.parent_name
                        label_ar = offlineField.label_ar
                        label_en = offlineField.label_en
                        options = tempList
                        isStored = offlineField.isStored
                    }
                    realm.insertOrUpdate(newOption)
                }
            }
            timesExecuted += 1

        }


        timesExecuted += 1
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
                copyOfDaFieldsData.postValue(param)

            }
        }
    }
}


