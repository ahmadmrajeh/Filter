package com.example.datascource.utils

import android.content.Context
import com.example.datascource.model.categAndSub.SooqFilterModel
import com.example.datascource.model.options.OptionsResponse
import com.example.datascource.model.searchRes.SearchRes
import com.example.datascource.utils.JsonMockApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NetworkDataSource {

    suspend   fun optionJsonToKotlin(applicationContext: Context): OptionsResponse {
        val jsonFileString = JsonMockApi.getJsonDataFromAsset(
            applicationContext, "dynamic.json"
        )
        val gson = Gson()
        val type = object : TypeToken<OptionsResponse>() {}.type
        return gson.fromJson(jsonFileString, type)
        //  offlineCacheFilterFields(optionsAndFields, orderedFields, id)
    }

    suspend  fun subFlowJsonToKotlin(applicationContext: Context): SearchRes {
        val jsonFileString = JsonMockApi.getJsonDataFromAsset(
            applicationContext,
            "assign.json")

        val gson = Gson()
        val type = object : TypeToken<SearchRes>() {}.type
        return gson.fromJson(
            jsonFileString,
            type
        ) /*optionJsonToKotlin(applicationContext, passedToOptions)*/
    }


    suspend   fun catJsonToKotlin(applicationContext: Context) : SooqFilterModel {
        val jsonFileString = JsonMockApi.getJsonDataFromAsset(
            applicationContext,
            "categories.json"
        )

        val gson = Gson()
        val type = object : TypeToken<com.example.datascource.model.
        categAndSub.SooqFilterModel>() {}.type
        return gson.fromJson(jsonFileString, type)
        //   offlineCacheCategories(gson.fromJson(jsonFileString, type))
    }
}