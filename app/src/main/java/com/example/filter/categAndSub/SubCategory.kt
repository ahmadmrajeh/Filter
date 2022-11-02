package com.example.filter.categAndSub

data class SubCategory(
    val has_child: Int,
    val icon: String,
    val id: Int,
    val label: String,
    val label_ar: String,
    val label_en: String,
    val name: String,
    val order: Int,
    val parent_id: Int,
    val reporting_name: String
)