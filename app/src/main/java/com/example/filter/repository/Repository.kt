package com.example.filter.repository

import com.example.filter.model.categAndSub.SooqFilterModel
import com.example.filter.realm.RealmOperations
import io.realm.Realm
 var realmOperations = RealmOperations()
class Repository {
    suspend fun insertItemToRealm(atItem: SooqFilterModel, db: Realm ) {
            realmOperations.insertListIntoRealm(atItem.result.data,db)


    }

}