package com.example.filter.realm

import com.example.filter.model.categAndSub.Item
import io.realm.RealmList
import io.realm.RealmObject

open class ResultCatRealm : RealmObject {
    lateinit var items: RealmList<CatItemRlm>

    constructor()
    constructor(items: RealmList<CatItemRlm>) : super() {
        this.items = items
    }


}