package com.example.datascource.model.options

data class Field(
    val data_type: String,
    val id: Int,
    val name: String,
    val parent_id: Int,
    val parent_name: String
)
