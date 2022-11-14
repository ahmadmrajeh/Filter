package com.example.filter.ui.screens.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datascource.model.options.OptionsResponse
import com.example.datascource.model.searchRes.SearchRes
import com.example.datascource.realm.category.CatItemRlm
import com.example.datascource.realm.category.ResultCatRealm
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
            "assign.json")

        val gson = Gson()
        val type = object : TypeToken<SearchRes>() {}.type
        val passedToOptions: SearchRes = gson.
        fromJson(jsonFileString, type)
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


    private fun offlineCacheCategories(modelItem: com.example.datascource.model
    .categAndSub.SooqFilterModel) {
        viewModelScope.launch(Dispatchers.IO) {
            apiDataCategory = repository.apiDataCategory(modelItem)
            val db = Realm.getDefaultInstance()
            repository.insertItemToRealm(apiDataCategory, db)
        }
    }


    private fun offlineCacheFilterFields(
        optionsAndFields:  OptionsResponse,
        orderedFields: SearchRes,
        id: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = Realm.getDefaultInstance()
            repository.insertFieldsToRealm(optionsAndFields, orderedFields, db, id)

        }
    }

      fun  updateOption(
      option: RealmOption,
      selected: Boolean
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            val db = Realm.getDefaultInstance()
            db.executeTransactionAwait(Dispatchers.Main) {
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
                }
                it.insertOrUpdate(newOption)
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


            data?.let {

                resultFilter.postValue(it)
            }




        }
    }


}