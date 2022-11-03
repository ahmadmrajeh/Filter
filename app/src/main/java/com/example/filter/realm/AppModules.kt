package com.example.filter.realm

  import io.realm.annotations.RealmModule


 @RealmModule(  classes = [CatItemRlm::class, SubCatRealm::class ,ResultCatRealm::class])
class AppModules {



}