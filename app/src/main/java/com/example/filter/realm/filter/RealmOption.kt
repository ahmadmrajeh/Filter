package com.example.filter.realm.filter

import io.realm.RealmObject

open class RealmOption : RealmObject {

    var field_id: String?=""
    var has_child: String?=""
    var id: String?=""
    var label: String?=""
    var label_en: String?=""
    var option_img: String?=""
    var order: String?=""
    var parent_id: String? =""
    var value: String?=""

    constructor()
    constructor(
        field_id: String?,
        has_child: String?,
        id: String?,
        label: String?,
        label_en: String?,
        option_img: String?,
        order: String?,
        parent_id: String?,
        value: String?
    ) : super() {
        this.field_id = field_id
        this.has_child = has_child
        this.id = id
        this.label = label
        this.label_en = label_en
        this.option_img = option_img
        this.order = order
        this.parent_id = parent_id
        this.value = value
    }


}