package com.example.datascource.realm.filter

import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

class RealmUpdate {
    fun copyRealmOption(optionActive: RealmOption, realmWrite: Realm): RealmOption? {
        val option = realmWrite.copyFromRealm(optionActive)
        realmWrite.close()
        return option
    }

    suspend fun updateOption(
        option: RealmOption,
        selected: Boolean,
        fromWhere: String?,
        db: Realm
    ) {
        db.executeTransactionAwait(Dispatchers.IO) {
            val newOption = RealmOption().apply {
                field_id = option.field_id
                has_child = option.has_child
                id = option.id
                label = option.label
                label_en = option.label_en
                option_img = option.option_img
                order = option.order
                parent_id = option.parent_id
                value = option.value
                isSelected = selected
                whereFrom = fromWhere
                parentIsSelected = false
            }
            it.insertOrUpdate(newOption)
        }
        db.close()
    }

    suspend fun updateChildOptions(
        option: RealmOption,
        selectedParent: Boolean,
        childrenWithSelectedParent: ArrayList<String>,
        modifiedFields: ArrayList<String>,
        realmWrite: Realm
    ): List<ArrayList<String>> {

        realmWrite.executeTransactionAwait(Dispatchers.IO) {
            val data =
                realmWrite.where(RealmOption::class.java)?.equalTo("parent_id", option.id)
                    ?.findAll()
            //this option is the parent of the elements in data
            if (data != null) {
                for (item in data) {
                    item.id?.let { itemId ->
                        if (selectedParent)
                            childrenWithSelectedParent.add(itemId)
                        else childrenWithSelectedParent.remove(itemId)
                    }
                    item.field_id?.let { it1 -> modifiedFields.add(it1) }
                    val newOption = RealmOption().apply {
                        field_id = item.field_id
                        has_child = item.has_child
                        id = item.id
                        label = item.label
                        label_en = item.label_en
                        option_img = item.option_img
                        order = item.order
                        parent_id = item.parent_id
                        value = item.value
                        isSelected = item.isSelected
                        whereFrom = item.whereFrom
                        parentIsSelected = selectedParent
                    }
                    it.insertOrUpdate(newOption)
                }
            }
        }

        realmWrite.close()
        return listOf(childrenWithSelectedParent, modifiedFields)
    }

    suspend fun getField(item: String, realmWrite: Realm): FieledRealm? {
        //realmWrite.close()
        return realmWrite
            .where(FieledRealm::class.java)?.equalTo("id", item.toInt())
            ?.findFirst()
    }

    fun copyRealmField(field: FieledRealm, realmWrite: Realm?): FieledRealm? {

        val copiedField = realmWrite?.copyFromRealm(field)
        realmWrite?.close()
        return copiedField

    }

    suspend fun modifyField(
        offlineField: FieledRealm, realmDb: Realm?, childrenWithSelectedParent: ArrayList<String>
    ) {
        realmDb?.executeTransactionAwait(Dispatchers.IO) { realm ->
            val tempList: RealmList<RealmOption> = RealmList()

            for (item in offlineField.optionsResistance) {
                if (item.id in childrenWithSelectedParent || item.parent_id == null)
                    tempList.add(item)
            }
            val newOption = FieledRealm().apply {
                data_type = offlineField.data_type
                id = offlineField.id
                name = offlineField.name
                parent_id = offlineField.parent_id
                parent_name = offlineField.parent_name
                label_ar = offlineField.label_ar
                label_en = offlineField.label_en
                options = tempList
                optionsResistance = offlineField.optionsResistance
            }
            realm.insertOrUpdate(newOption)
        }
        realmDb?.close()
    }


}