package com.uwo.databaseexploration.repository

interface CustomerRepository {
    suspend fun getCustomers(): List<Customer>
    suspend fun findByName(firstName: String, lastName: String): List<Customer>
    suspend fun insertCustomers(customers: List<Customer>)
    suspend fun deleteAllCustomers()
}