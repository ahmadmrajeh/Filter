package com.example.filter.realm

  import com.example.filter.realm.category.CatItemRlm
  import com.example.filter.realm.category.ResultCatRealm
  import com.example.filter.realm.category.SubCatRealm
  import com.example.filter.realm.filter.FieledRealm
  import com.example.filter.realm.filter.FilterSubCategory
  import com.example.filter.realm.filter.RealmOption
  import io.realm.annotations.RealmModule

 @RealmModule(  classes = [CatItemRlm::class, SubCatRealm::class , ResultCatRealm::class,  FieledRealm::class,FilterSubCategory::class, RealmOption::class])
class AppModules
