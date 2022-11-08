package com.example.datascource.model.options

import com.example.datascource.model.options.Data

data class Result(
    val `data`: Data,
    val hash: String,
    val status: Int
)