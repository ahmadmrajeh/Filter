package com.example.datascource.realm.filter

 import com.example.datascource.model.options.Field
import com.example.datascource.model.options.Option
import com.example.datascource.model.options.OptionsResponse
 import com.example.datascource.model.searchRes.SearchFlow
import com.example.datascource.model.searchRes.SearchRes
 import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

class RealmFilterOperations {

  suspend fun insertFieldsIntoRealm(
      optionsAndFields: OptionsResponse,
      orderedFields: SearchRes,
      db: Realm,
      id: Int
    ) {
            val fieldsRealmList: RealmList<FieledRealm> =  getApiFields(optionsAndFields , orderedFields , id )



      db.executeTransactionAwait(Dispatchers.IO) {
                val filterSubCategory = FilterSubCategory().apply {
                    idSubCategory = id
                    fieldsList=fieldsRealmList
                }
                it.insertOrUpdate(filterSubCategory)
            }
    }

    private fun orderedFieldsToRealm(
        orderedFields: SearchRes,
        id: Int,
        optionsAndFields: OptionsResponse,
        fieldsRealmList: RealmList<FieledRealm>
    )  {
        val tempList: SearchFlow? = orderedFields.result.data.search_flow.find {
            it.category_id == id
        }

        for (item in tempList!!.order) {
            val listElem = orderedFields.result.data.fields_labels.find {
                it.field_name == item
            }

            val temp: Field? = optionsAndFields.result.data.fields.find {
                it.name == item

            }
           val optionsForCurrentField: List<Option> = optionsAndFields.result.data.options.filter {
               temp!!.id.toString() == it.field_id
           }
            val realmOptions :RealmList<RealmOption> = changeOptionsTypeToRealm(optionsForCurrentField)

            temp?.let { field->

                    fieldsRealmList.add(
                        FieledRealm(field.data_type, field.id, field.name, field.parent_id,
                            field.parent_name,listElem!!.label_ar, listElem.label_en,realmOptions)
                    )

            }
        }
    }

    private fun changeOptionsTypeToRealm(optionsForCurrentField: List<Option>): RealmList<RealmOption> {
      val returnedList : RealmList<RealmOption> = RealmList()
      for (element in optionsForCurrentField){

              returnedList.add(
                  RealmOption(
                      element.field_id,element.has_child,element.id,element.label,
                      element.label_en,
                      "https://opensooqui2.os-cdn.com/api/apiV/android/xxh"+element.option_img,
                      element.order,element.parent_id,element.value
                  )
              )




      }
        return returnedList
    }

    fun getApiFields(optionsAndFields: OptionsResponse, orderedFields: SearchRes, id: Int): RealmList<FieledRealm> {
        val fieldsRealmList: RealmList<FieledRealm> = RealmList()
        orderedFieldsToRealm(orderedFields, id, optionsAndFields, fieldsRealmList)
        return fieldsRealmList
    }
}