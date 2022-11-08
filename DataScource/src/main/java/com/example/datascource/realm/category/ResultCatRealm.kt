package com.example.datascource.realm.category

import com.example.datascource.realm.category.CatItemRlm
import io.realm.RealmList
import io.realm.RealmObject

open class ResultCatRealm : RealmObject {
    lateinit var items: RealmList<CatItemRlm>

    constructor()
    constructor(items: RealmList<CatItemRlm>) : super() {
        this.items = items
    }


}