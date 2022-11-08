package com.example.filter.repository

import com.example.datascource.model.categAndSub.SooqFilterModel
import com.example.datascource.model.options.OptionsResponse
import com.example.datascource.model.searchRes.SearchRes
import com.example.datascource.realm.category.RealmCategoryOperations
import com.example.datascource.realm.filter.RealmFilterOperations
import io.realm.Realm

class Repository {
    suspend fun insertItemToRealm(atItem: SooqFilterModel, db: Realm ) {
        val realmCategoryOperations = RealmCategoryOperations()
        realmCategoryOperations.insertListIntoRealm(atItem.result.data,db)


    }

    suspend fun insertFieldsToRealm(
        optionsAndFields: OptionsResponse,
        orderedFields: SearchRes,
        db: Realm,
        id: Int
    ) {
        val realmFilterOperations = RealmFilterOperations()
        realmFilterOperations.insertFieldsIntoRealm(optionsAndFields,orderedFields,db,id)


    }

}