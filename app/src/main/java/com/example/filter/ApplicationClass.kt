package com.example.filter

import android.app.Application
 import com.example.filter.realm.AppModules
 import io.realm.Realm
import io.realm.RealmConfiguration


class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init (this)
        val config = RealmConfiguration.Builder().schemaVersion(6).modules(
            AppModules()
        )
            .deleteRealmIfMigrationNeeded()
            .name("realm.db")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(false)
            .build()

        Realm.setDefaultConfiguration(config)

    }
}