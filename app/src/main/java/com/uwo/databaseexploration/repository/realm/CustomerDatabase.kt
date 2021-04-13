package com.uwo.databaseexploration.repository.realm

import io.realm.RealmConfiguration

class CustomerDatabase(realmConfig: RealmConfiguration) {
    private val customerDao = CustomerDao(realmConfig = realmConfig)

    fun customerDao() = customerDao
}