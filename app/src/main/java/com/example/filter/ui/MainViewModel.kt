package com.example.filter.ui

 import android.content.Context
 import android.util.Log
 import androidx.lifecycle.MutableLiveData
 import androidx.lifecycle.ViewModel
 import androidx.lifecycle.viewModelScope
 import com.example.filter.model.categAndSub.SooqFilterModel
 import com.example.filter.model.options.OptionsResponse
 import com.example.filter.model.searchRes.SearchRes
 import com.example.filter.realm.ResultCatRealm
 import com.example.filter.repository.Repository
 import com.example.filter.utils.JsonMockApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
 import io.realm.Realm
 import io.realm.RealmList
 import kotlinx.coroutines.Dispatchers
 import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
var repository = Repository()

    var result: MutableLiveData<ResultCatRealm> = MutableLiveData()

     fun optionJsonToKotlin(applicationContext: Context): OptionsResponse {
        val jsonFileString = JsonMockApi.getJsonDataFromAsset(applicationContext
            , "dynamic.json")
        Log.i("data", jsonFileString!!)
        val gson = Gson()
        val  type = object : TypeToken<OptionsResponse>() {}.type
        return gson.fromJson(jsonFileString, type)
    }

    fun subFlowJsonToKotlin(applicationContext: Context): SearchRes {
        val jsonFileString = JsonMockApi.getJsonDataFromAsset(applicationContext,
            "assign.json")
        Log.i("data1", jsonFileString!!)
        val gson = Gson()
        val type = object : TypeToken<SearchRes>() {}.type
        return gson.fromJson(jsonFileString, type)
    }


      fun catJsonToKotlin(applicationContext: Context)  {
        val jsonFileString = JsonMockApi.getJsonDataFromAsset(applicationContext,
            "categories.json")

        Log.i("data2", jsonFileString!!)
        val gson = Gson()
        val type = object : TypeToken<SooqFilterModel>() {}.type
          offlineCache( gson.fromJson(jsonFileString, type))
    }


    private fun offlineCache(modelItem: SooqFilterModel) {

        viewModelScope.launch (Dispatchers.IO) {
            val db = Realm.getDefaultInstance()
            repository.insertItemToRealm(modelItem,db)
        }


    }

    fun readOfflineCache() {

        viewModelScope.launch(Dispatchers.Main) {

            val db: Realm=Realm.getDefaultInstance()
            val data=db.where(ResultCatRealm::class.java)?.findFirst()

            data?.let { result.postValue(it)
            }
        }

    }



    /* val listPersonType = object : TypeToken<List<SooqFilterModel>>() {}.type

     val persons: List<SooqFilterModel> = gson.fromJson(jsonFileString, listPersonType)*/

}