package com.example.datascource.repository

import android.content.Context
import com.example.datascource.utils.NetworkDataSource
import com.example.datascource.model.categAndSub.SooqFilterModel
import com.example.datascource.model.options.OptionsResponse
import com.example.datascource.model.searchRes.SearchRes
import com.example.datascource.realm.AppModules
import com.example.datascource.realm.category.CatItemRlm
import com.example.datascource.realm.category.RealmCategoryOperations
import com.example.datascource.realm.category.ResultCatRealm
import com.example.datascource.realm.filter.*
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList


class Repository(context: Context) {
    private val applicationContext = context
    private val realmFilterOperations = RealmFilterOperations()
    private val realmCategoryOperations = RealmCategoryOperations()
   val updateRealm = RealmUpdate()
private val  networkDataSource = NetworkDataSource()
    init {
        Realm.init(context)
         val config = RealmConfiguration.Builder().schemaVersion(8).modules(
        AppModules()
        )
            .deleteRealmIfMigrationNeeded()
            .name("realm.db")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(false)
            .build()
         Realm.setDefaultConfiguration(config)
    }


    suspend fun insertItemToRealm(atItem: RealmList<CatItemRlm>) {
        val realmWrite = Realm.getDefaultInstance()
        realmCategoryOperations.insertListIntoRealm(atItem, realmWrite)
    }

    suspend fun insertFieldsToRealm(
        optionsAndFields: OptionsResponse,
        orderedFields: SearchRes,
        id: Int
    ) {
        val realmWrite = Realm.getDefaultInstance()
        realmFilterOperations.insertFieldsIntoRealm(optionsAndFields, orderedFields, realmWrite, id)
    }

    suspend fun optionToKotlin() : OptionsResponse{
        return networkDataSource.optionJsonToKotlin(applicationContext)
    }

    suspend fun subFlowJsonToKotlin(): SearchRes {
        return networkDataSource.subFlowJsonToKotlin(applicationContext)
    }


    suspend fun catJsonToKotlin(): SooqFilterModel {
        return networkDataSource.catJsonToKotlin(applicationContext)
    }

    fun apiDataCategory(atItem: SooqFilterModel): RealmList<CatItemRlm> {
        val realmCategoryOperations = RealmCategoryOperations()
        return realmCategoryOperations.getCategoryFromJson(atItem.result.data)
    }

    fun readOfflineCacheCategoriesAndSub(): ResultCatRealm? {
        val db: Realm = Realm.getDefaultInstance()
      return  realmCategoryOperations.readOfflineCacheCategoriesAndSub(db)

    }

    fun readOfflineCacheFields(id: Int): FilterSubCategory? {
        val db: Realm = Realm.getDefaultInstance()
        return  realmFilterOperations.readOfflineCacheFields(db,id)

    }


    // realm Uodates
    fun copyRealmOption( optionActive: RealmOption): RealmOption? {
        val realmWrite = Realm.getDefaultInstance()
        return updateRealm.copyRealmOption(optionActive,realmWrite)
    }

    suspend fun updateOption(option: RealmOption,
                      selected: Boolean, fromWhere: String?
    )  {   val db = Realm.getDefaultInstance()
        return updateRealm.updateOption( option , selected  , fromWhere,db)
    }

      suspend fun updateChildOptions(
          option: RealmOption,
          selectedParent: Boolean,
          childrenWithSelectedParent: ArrayList<String>,
          modifiedFields: ArrayList<String>
      ): List<ArrayList<String>> {
          val realmWrite = Realm.getDefaultInstance()
        return updateRealm.updateChildOptions(option,selectedParent
            ,childrenWithSelectedParent,modifiedFields,realmWrite)

    }

    suspend fun getField(item: String): FieledRealm? {
        val realmWrite = Realm.getDefaultInstance()
        return  updateRealm.getField(item, realmWrite)
    }

    fun copyRealmField( field: FieledRealm): FieledRealm? {
        val realmWrite = Realm.getDefaultInstance()
        return updateRealm.copyRealmField(field,realmWrite)
    }
    suspend fun modifyField(
        offlineField: FieledRealm,
        childrenWithSelectedParent: ArrayList<String>
    ) {
        val realmDb = Realm.getDefaultInstance()
        return  updateRealm.modifyField(offlineField, realmDb,childrenWithSelectedParent)
    }
 }
