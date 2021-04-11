package com.uwo.databaseexploration.repository.realm

import io.realm.Realm

class CustomerDatabase constructor(
    realm: Realm
) {
    private val customerDao = CustomerDao()

    fun customerDao() = customerDao
}