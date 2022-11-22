package com.example.datascource.realm.category


import android.util.Log
import com.example.datascource.model.categAndSub.Data
import com.example.datascource.model.categAndSub.Item
import com.example.datascource.model.categAndSub.SubCategory
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

class RealmCategoryOperations {
    fun getCategoryFromJson(itemToDatabase: Data): RealmList<CatItemRlm> {
        return responseToRlms(itemToDatabase.items)
    }

    suspend fun insertListIntoRealm(itemToDatabase: RealmList<CatItemRlm>, db: Realm) {

        try {
            db.executeTransactionAwait(Dispatchers.IO) {
                val categories = ResultCatRealm().apply {
                    items = itemToDatabase
                    id = 0
                }

                it.insertOrUpdate(categories)

            }
        } catch (e: Exception) {
            Log.e("realm insert error", e.message.toString())
        }

    }

    private fun responseToRlms(itemToDatabase: List<Item>):
            RealmList<CatItemRlm> {

        val itemInRealm: RealmList<CatItemRlm> = RealmList()
        for (element in itemToDatabase) {
            itemInRealm.add(
                CatItemRlm(
                    element.has_child, element.icon, element.id, element.label,
                    element.label_ar, element.label_en, element.name, element.order,
                    element.id, element.reporting_name, getSubCateg(element.subCategories)
                )
            )
        }
        return itemInRealm
    }

    private fun getSubCateg(subCategories: List<SubCategory>): RealmList<SubCatRealm> {
        val itemInRealm: RealmList<SubCatRealm> = RealmList()
        for (element in subCategories) {
            itemInRealm.add(
                SubCatRealm(
                    element.has_child,
                    element.icon,
                    element.id,
                    element.label,
                    element.label_ar,
                    element.label_en,
                    element.name,
                    element.order,
                    element.parent_id,
                    element.reporting_name

                )
            )
        }
        return itemInRealm
    }

    fun readOfflineCacheCategoriesAndSub(db: Realm): ResultCatRealm? {
            val data = db.where(ResultCatRealm::class.java)?.findFirst()
       // db.close()
           return data
        }



}