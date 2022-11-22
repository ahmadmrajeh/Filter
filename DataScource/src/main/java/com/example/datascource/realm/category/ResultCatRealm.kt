package com.example.datascource.realm.category

import com.example.datascource.realm.category.CatItemRlm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ResultCatRealm : RealmObject {
 lateinit var items: RealmList<CatItemRlm>
    @PrimaryKey var id :Int=0
    constructor()
    constructor(items: RealmList<CatItemRlm>, id: Int) : super() {
        this.items = items
        this.id = id
    }
}