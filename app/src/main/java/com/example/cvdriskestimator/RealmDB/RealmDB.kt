package com.example.cvdriskestimator.RealmDB

import android.content.Context
import com.example.cvdriskestimator.MainActivity
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmDB(appContext: Context) {

    private var realmConfiguration: RealmConfiguration

    init {
        Realm.init(appContext)
        realmConfiguration = RealmConfiguration.Builder()
            .name("patients.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }

}