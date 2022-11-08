package com.example.filter.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datascource.model.options.OptionsResponse
import com.example.datascource.realm.category.ResultCatRealm
import com.example.datascource.realm.filter.FilterSubCategory
import com.example.filter.repository.Repository
import com.example.filter.utils.JsonMockApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    var repository = Repository()
    var result: MutableLiveData<ResultCatRealm> = MutableLiveData()
    var resultFilter: MutableLiveData<FilterSubCategory> = MutableLiveData()

    private fun optionJsonToKotlin(applicationContext: Context, orderedFields: com.example.datascource.model.searchRes.SearchRes, id: Int) {
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
        val type = object : TypeToken<com.example.datascource.model.searchRes.SearchRes>() {}.type
        val passedToOptions: com.example.datascource.model.searchRes.SearchRes = gson.
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
            val db = Realm.getDefaultInstance()
            repository.insertItemToRealm(modelItem, db)
        }
    }


    private fun offlineCacheFilterFields(
        optionsAndFields:  OptionsResponse,
        orderedFields: com.example.datascource.model.searchRes.SearchRes,
        id: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = Realm.getDefaultInstance()
            repository.insertFieldsToRealm(optionsAndFields, orderedFields, db, id)
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
            val data =
                db.where(FilterSubCategory::class.java)
                    .equalTo("idSubCategory", id)?.findFirst()
            data?.let {
                resultFilter.postValue(it)


            }

        }
    }


}