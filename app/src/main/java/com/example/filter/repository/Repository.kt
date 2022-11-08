package com.example.filter.repository

import com.example.filter.model.categAndSub.SooqFilterModel
import com.example.filter.model.options.OptionsResponse
import com.example.filter.model.searchRes.SearchRes
import com.example.filter.realm.category.RealmCategoryOperations
import com.example.filter.realm.filter.RealmFilterOperations
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