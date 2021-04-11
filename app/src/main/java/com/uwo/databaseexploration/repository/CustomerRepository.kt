package com.uwo.databaseexploration.repository

import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun getCustomers(): Flow<List<Customer>>
    fun findByName(firstName: String, lastName: String): Flow<List<Customer>>
}