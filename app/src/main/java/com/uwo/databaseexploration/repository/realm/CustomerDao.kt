package com.uwo.databaseexploration.repository.realm

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait

class CustomerDao(
    private val realmConfig: RealmConfiguration
) {
    fun getAllCustomers(): List<Customer> {
        val customerQuery = getRealmInstance().where(Customer::class.java)
        return customerQuery.findAll().toList()
    }

    fun getCustomersByName(firstName: String, lastName: String): List<Customer> {
        val customerQuery = getRealmInstance().where(Customer::class.java)
        return customerQuery.equalTo("firstName", firstName).equalTo("lastName", lastName).findAll().toList()
    }

    suspend fun insertCustomers(customers: List<Customer>) {
        getRealmInstance().executeTransactionAwait { realm ->
            realm.insertOrUpdate(customers)
        }
    }

    suspend fun deleteAllCustomers() {
        getRealmInstance().executeTransactionAwait { realm ->
            realm.delete(Customer::class.java)
        }
    }

    private fun getRealmInstance(): Realm {
        return Realm.getInstance(realmConfig)
    }
}