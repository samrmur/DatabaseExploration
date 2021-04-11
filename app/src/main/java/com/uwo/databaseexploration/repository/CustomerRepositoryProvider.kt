package com.uwo.databaseexploration.repository

import android.content.SharedPreferences
import com.uwo.databaseexploration.core.shouldUseRoom
import com.uwo.databaseexploration.repository.room.CustomerDatabase as CustomerRoomDatabase
import com.uwo.databaseexploration.repository.realm.CustomerDatabase as CustomerRealmDatabase
import com.uwo.databaseexploration.repository.room.CustomerRepository as CustomerRoomRepository
import com.uwo.databaseexploration.repository.realm.CustomerRepository as CustomerRealmRepository
import javax.inject.Provider

class CustomerRepositoryProvider(
    roomDatabase: CustomerRoomDatabase,
    realmDatabase: CustomerRealmDatabase,
    private val sharedPreferences: SharedPreferences
) : Provider<CustomerRepository> {
    private val roomRepository = CustomerRoomRepository(roomDatabase.customerDao())
    private val realmRepository = CustomerRealmRepository(realmDatabase.customerDao())

    override fun get(): CustomerRepository {
        return if (sharedPreferences.shouldUseRoom()) roomRepository else realmRepository
    }
}