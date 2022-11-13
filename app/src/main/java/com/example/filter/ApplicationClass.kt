package com.example.filter

import android.app.Application
 import com.example.datascource.realm.AppModules
 import io.realm.Realm
import io.realm.RealmConfiguration


class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init (this)
        val config = RealmConfiguration.Builder().schemaVersion(7).modules(
            com.example.datascource.realm.AppModules()
        )
            .deleteRealmIfMigrationNeeded()
            .name("realm.db")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(false)
            .build()

        Realm.setDefaultConfiguration(config)

    }
}