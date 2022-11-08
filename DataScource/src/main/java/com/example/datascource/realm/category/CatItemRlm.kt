package com.example.datascource.realm.category

import io.realm.RealmList
import io.realm.RealmObject

open class CatItemRlm : RealmObject {
    var has_child: Int= 0
    var icon: String =""
    var id: Int= 0
    var label: String=""
    var label_ar: String=""
    var label_en: String=""
    var name: String=""
    var order: Int= 0
    var parent_id: Int= 0
    var reporting_name: String=""
    lateinit var subCategories: RealmList<SubCatRealm>

    constructor()
    constructor(
        has_child: Int,
        icon: String,
        id: Int,
        label: String,
        label_ar: String,
        label_en: String,
        name: String,
        order: Int,
        parent_id: Int,
        reporting_name: String,
        subCategories: RealmList<SubCatRealm>
    ) : super() {
        this.has_child = has_child
        this.icon = icon
        this.id = id
        this.label = label
        this.label_ar = label_ar
        this.label_en = label_en
        this.name = name
        this.order = order
        this.parent_id = parent_id
        this.reporting_name = reporting_name
        this.subCategories = subCategories
    }
}