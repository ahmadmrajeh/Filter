package com.example.filter.realm.filter

import io.realm.RealmList
import io.realm.RealmObject

open class FieledRealm : RealmObject {
    var data_type: String?=""
    var id: Int?=0
    var name: String?=""
    var parent_id: Int?=0
    var parent_name: String?=""
    var label_ar: String?=""
    var label_en: String?=""
    lateinit var options :RealmList<RealmOption>
    constructor()
    constructor(
        data_type: String?,
        id: Int?,
        name: String?,
        parent_id: Int?,
        parent_name: String?,
        label_ar: String?,
        label_en: String?,
        options: RealmList<RealmOption>
    ) : super() {
        this.data_type = data_type
        this.id = id
        this.name = name
        this.parent_id = parent_id
        this.parent_name = parent_name
        this.label_ar = label_ar
        this.label_en = label_en
        this.options = options
    }


}