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
import io.realm.kotlin.executeTransactionAwait
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


    private var childrenWithSelectedParent: RealmList<RealmOption> = RealmList()

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
        option: RealmOption,
        selected: Boolean,
        fromWhere: String?
    ) {

        viewModelScope.launch(Dispatchers.Main) {
            val realmWrite = Realm.getDefaultInstance()
            realmWrite.executeTransactionAwait(Dispatchers.Main) {
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
            updateChildOptions(option, selected)
        }
    }

    private fun updateChildOptions(
        option: RealmOption,
        selectedParent: Boolean
    ) {

        val modifiedFields: ArrayList<String> = ArrayList()

        viewModelScope.launch(Dispatchers.Main) {
            val realmWrite = Realm.getDefaultInstance()
            realmWrite.executeTransactionAwait(Dispatchers.Main) {
                val data =
                    realmWrite.where(RealmOption::class.java)?.equalTo("parent_id", option.id)
                        ?.findAll()
                //this option is the parent of the elements in data
                if (data != null) {
                    for (item in data) {
                        if (selectedParent) childrenWithSelectedParent.add(item)
                        else childrenWithSelectedParent.remove(item)
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

            }
            updateOptionsList(modifiedFields)
        }

    }

    private fun updateOptionsList(
        modifiedFields: ArrayList<String>
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            val realmWrite = Realm.getDefaultInstance()

            realmWrite.executeTransactionAwait(Dispatchers.Main) { realm ->

                val tempList: RealmList<RealmOption> = RealmList()

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
        offlineField: FieledRealm
    ) {


        viewModelScope.launch(Dispatchers.Main) {
            val realmWrite = Realm.getDefaultInstance()

            realmWrite.executeTransactionAwait(Dispatchers.Main) { realm ->

                if (fieldOriginalData[offlineField.id.toString()]?.name.isNullOrEmpty()) {
                    fieldOriginalData[offlineField.id.toString()] =
                        realmWrite.copyFromRealm(offlineField)
                }
                val tempList: RealmList<RealmOption> = RealmList()
                fieldOriginalData[offlineField.id.toString()]?.let {
                    for (item in it.options) {
                        val existed = childrenWithSelectedParent.find { i ->
                            item.id == i.id
                        }
                        if (!existed?.id.isNullOrEmpty() || item.parent_id == null)
                            tempList.add(item)
                    }
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
                copyOfDaFieldsData.postValue(param)

            }
            initializeFields(db)
        }
    }

    private fun initializeFields(db: Realm) {
 val realmOptionInit =    db.where(RealmOption::class.java).equalTo("id","143").findFirst()
        realmOptionInit?.let {
            updateOption(it,true,"")
            updateOption(it,false,"")
        }

    }
}


