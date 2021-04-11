package com.uwo.databaseexploration.repository.realm

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CustomerDao {
    fun getAllCustomers(): Flow<List<Customer>> {
        return flow {  }
    }

    fun getCustomersByName(firstName: String, lastName: String): Flow<List<Customer>> {
        return flow {  }
    }
}