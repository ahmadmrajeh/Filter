package com.example.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.filter.categAndSub.SooqFilterModel
import com.example.filter.utils.JsonMockApi.Companion.getJsonDataFromAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val persons: SooqFilterModel = jsonToKotlin()

        Log.i("data2", "> Item  "+persons.result.data.items .toString())
       /* val listPersonType = object : TypeToken<List<SooqFilterModel>>() {}.type

        val persons: List<SooqFilterModel> = gson.fromJson(jsonFileString, listPersonType)*/


    }

    private fun jsonToKotlin(): SooqFilterModel {
        val jsonFileString = getJsonDataFromAsset(applicationContext, "categories.json")
        Log.i("data", jsonFileString!!)

        val gson = Gson()
        val personType = object : TypeToken<SooqFilterModel>() {}.type

        val persons: SooqFilterModel = gson.fromJson(jsonFileString, personType)
        return persons
    }
}
