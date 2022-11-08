package com.example.datascource.model.searchRes

import com.example.datascource.model.searchRes.Data

data class Result(
    val `data`: Data,
    val hash: String,
    val status: Int
)