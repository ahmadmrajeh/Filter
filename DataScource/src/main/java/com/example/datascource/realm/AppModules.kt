package com.example.datascource.realm

  import com.example.datascource.realm.category.CatItemRlm
  import com.example.datascource.realm.category.ResultCatRealm
  import com.example.datascource.realm.category.SubCatRealm
  import com.example.datascource.realm.filter.FieledRealm
  import com.example.datascource.realm.filter.FilterSubCategory
  import com.example.datascource.realm.filter.RealmOption
  import io.realm.annotations.RealmModule

 @RealmModule(  classes = [CatItemRlm::class, SubCatRealm::class , ResultCatRealm::class,  FieledRealm::class, FilterSubCategory::class, RealmOption::class])
class AppModules
