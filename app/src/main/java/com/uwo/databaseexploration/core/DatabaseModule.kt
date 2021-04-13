package com.uwo.databaseexploration.core

import android.app.Application
import androidx.room.Room
import com.uwo.databaseexploration.repository.CustomerRepository
import com.uwo.databaseexploration.repository.CustomerRepositoryProvider
import com.uwo.databaseexploration.repository.realm.CustomerDatabase as CustomerRealmDatabase
import com.uwo.databaseexploration.repository.room.CustomerDatabase as CustomerRoomDatabase
import io.realm.Realm
import io.realm.RealmConfiguration
import toothpick.config.Module
import toothpick.ktp.binding.bind

class DatabaseModule(
    application: Application
): Module() {
    companion object {
        private const val CUSTOMER_DATABASE = "customer-database"
    }

    init {
        val applicationContext = application.applicationContext

        // Build room database
        val roomDatabase = Room.databaseBuilder(
            applicationContext,
            CustomerRoomDatabase::class.java,
            CUSTOMER_DATABASE
        ).build()

        // Initialize realm database
        Realm.init(applicationContext)

        // Build configuration
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()

        // Build realm database
        val realmDatabase = CustomerRealmDatabase(realmConfig = realmConfig)

        bind<CustomerRepository>().toProviderInstance(CustomerRepositoryProvider(
            roomDatabase = roomDatabase,
            realmDatabase = realmDatabase,
            sharedPreferences = applicationContext.getSharedPreferences(DATABASE_PREFERENCES_FILE, 0)
        ))
    }
}