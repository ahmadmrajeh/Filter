package com.example.filter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.filter.R
import com.example.filter.databinding.ActivityMainBinding
import com.example.filter.databinding.FragmentMainBinding
import com.example.filter.model.categAndSub.SooqFilterModel
import com.example.filter.utils.JsonMockApi.Companion.getJsonDataFromAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}



