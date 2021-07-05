package com.vk59.diary_simbirsoft.app

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.coroutineScope

class DiaryApplication : Application() {
    companion object {
        lateinit var config: RealmConfiguration
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        config = RealmConfiguration
            .Builder()
//            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}