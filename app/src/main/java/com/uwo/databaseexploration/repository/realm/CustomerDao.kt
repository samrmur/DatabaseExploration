package com.uwo.databaseexploration.repository.realm

import io.realm.Realm

class CustomerDao(private val realm: Realm) {
    fun getAllCustomers(): List<Customer> {
        val customerQuery = realm.where(Customer::class.java)
        return customerQuery.findAll().toList()
    }

    fun getCustomersByName(firstName: String, lastName: String): List<Customer> {
        val customerQuery = realm.where(Customer::class.java)
        return customerQuery.equalTo("firstName", firstName).equalTo("lastName", lastName).findAll().toList()
    }
}