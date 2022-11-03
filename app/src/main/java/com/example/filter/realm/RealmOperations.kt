package com.example.filter.realm


import android.util.Log
import com.example.filter.model.categAndSub.Data
import com.example.filter.model.categAndSub.Item
import com.example.filter.model.categAndSub.SubCategory
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

   class RealmOperations {


    suspend fun insertListIntoRealm(itemToDatabase: Data, db: Realm)  {

            val realmResults: RealmList<CatItemRlm> = responseToRlms(itemToDatabase.items)
        try {

           db .executeTransactionAwait(Dispatchers.IO){

                val categories = ResultCatRealm().apply {
                  items=realmResults
                }

                it.insertOrUpdate(categories)

            }
        Log.e("inserts","it inserts")
        } catch (e: Exception) {
        Log.e("realm insert error",e.message.toString())
        }




        }

    private fun responseToRlms(itemToDatabase: List<Item>):
            RealmList<CatItemRlm> {

        val itemInRealm: RealmList<CatItemRlm> = RealmList()
        for (element in itemToDatabase) {
            itemInRealm.add(
                CatItemRlm(
                    element.has_child,element.icon,element.id,element.label,
                    element.label_ar,element.label_en,element.name,element.order,
                    element.id,element.reporting_name,getSubCateg(element.subCategories)
                )
            )
        }
        return itemInRealm
    }

    private fun getSubCateg(subCategories: List<SubCategory>): RealmList<SubCatRealm> {
        val itemInRealm: RealmList<SubCatRealm> = RealmList()
        for (element in subCategories){
            itemInRealm.add(
                SubCatRealm(
                element.has_child,element.icon,
                element.id, element.label, element.label_ar,element.label_en, element.name
            ,element.order, element.parent_id, element.reporting_name
            ))
        }

      return  itemInRealm
    }


}