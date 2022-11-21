package com.example.datascource.repository

import com.example.datascource.model.categAndSub.SooqFilterModel
import com.example.datascource.model.options.OptionsResponse
import com.example.datascource.model.searchRes.SearchRes
import com.example.datascource.realm.category.CatItemRlm
import com.example.datascource.realm.category.RealmCategoryOperations
import com.example.datascource.realm.filter.FieledRealm
import com.example.datascource.realm.filter.RealmFilterOperations
import com.example.datascource.realm.filter.RealmOption
import io.realm.Realm
import io.realm.RealmList

class Repository {
    suspend fun insertItemToRealm(atItem: RealmList<CatItemRlm>, db: Realm ) {
        val realmCategoryOperations = RealmCategoryOperations()
        realmCategoryOperations.insertListIntoRealm(atItem ,db)


    }

    suspend fun insertFieldsToRealm(
        optionsAndFields: OptionsResponse,
        orderedFields: SearchRes,
        db: Realm,
        id: Int
    )  {
        val realmFilterOperations = RealmFilterOperations()


     realmFilterOperations.insertFieldsIntoRealm(optionsAndFields,orderedFields,db,id)


    }


 fun apiDataCategory (atItem: SooqFilterModel ): RealmList<CatItemRlm> {
    val realmCategoryOperations = RealmCategoryOperations()
  return realmCategoryOperations. getCategoryFromJson(atItem.result.data)
 }

}