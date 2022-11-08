package com.example.filter.realm.category

import io.realm.RealmList
import io.realm.RealmObject

open class ResultCatRealm : RealmObject {
    lateinit var items: RealmList<CatItemRlm>

    constructor()
    constructor(items: RealmList<CatItemRlm>) : super() {
        this.items = items
    }


}