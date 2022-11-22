package com.example.datascource.realm.category

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SubCatRealm: RealmObject {
    var has_child: Int= 0
    var icon: String=""
@PrimaryKey var id: Int= 0
    var label: String=""
    var label_ar: String=""
    var label_en: String=""
    var name: String=""
    var order: Int= 0
    var parent_id: Int= 0
    var reporting_name: String=""

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
        reporting_name: String
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
    }


}