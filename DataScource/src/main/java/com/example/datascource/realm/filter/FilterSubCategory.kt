package com.example.datascource.realm.filter

import com.example.datascource.realm.filter.FieledRealm
import io.realm.RealmList
import io.realm.RealmObject

open class FilterSubCategory : RealmObject {
    lateinit var fieldsList: RealmList<FieledRealm>
    var idSubCategory: Int = 0

    constructor()
    constructor(
        fieldsList: RealmList<FieledRealm>,
        idSubCategory: Int
    ) : super() {
        this.fieldsList = fieldsList
        this.idSubCategory = idSubCategory
    }
}