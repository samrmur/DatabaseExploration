package com.uwo.databaseexploration.core

import android.app.Application
import android.content.Context
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
        private const val DATABASE_PREFERENCES = "database_preferences"
        private const val CUSTOMER_DATABASE = "customer-database"
    }

    private val applicationContext = application.applicationContext

    private val roomDatabase = Room.databaseBuilder(
        applicationContext,
        CustomerRoomDatabase::class.java,
        CUSTOMER_DATABASE
    ).build()

    private val realmConfig = RealmConfiguration.Builder().build()

    private val realmDatabase = CustomerRealmDatabase(Realm.getInstance(realmConfig))

    init {
        Realm.init(applicationContext)

        bind<CustomerRepository>().toProviderInstance(CustomerRepositoryProvider(
            roomDatabase = roomDatabase,
            realmDatabase = realmDatabase,
            sharedPreferences = applicationContext.getSharedPreferences(DATABASE_PREFERENCES, 0)
        ))
    }
}