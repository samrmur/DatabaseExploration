package com.uwo.databaseexploration.repository

import com.uwo.databaseexploration.repository.room.Customer as RoomCustomer
import com.uwo.databaseexploration.repository.realm.Customer as RealmCustomer

/**
 * Main domain model that represents a customer
 */
data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val company: String,
    val streetAddress: String,
    val city: String,
    val region: String,
    val country: String,
    val postalCode: String,
    val totalSpent: String,
    val totalOrders: Int,
    val acceptsMarketing: Boolean,
    val taxExempt: Boolean
)

/**
 * Converts a room customer into a domain customer
 * @return Customer
 */
fun RoomCustomer.toDomainCustomer(): Customer {
    return Customer(
        id = id,
        firstName = firstName,
        lastName = lastName,
        phone = phone,
        company = company,
        streetAddress = streetAddress,
        city = city,
        region = region,
        country = country,
        postalCode = postalCode,
        totalSpent = totalSpent,
        totalOrders = totalOrders,
        acceptsMarketing = acceptsMarketing,
        taxExempt = taxExempt
    )
}

/**
 * Converts a realm customer into a domain customer
 * @return Customer
 */
fun RealmCustomer.toDomainCustomer(): Customer {
    return Customer(
        id = id,
        firstName = firstName,
        lastName = lastName,
        phone = phone,
        company = company,
        streetAddress = streetAddress,
        city = city,
        region = region,
        country = country,
        postalCode = postalCode,
        totalSpent = totalSpent,
        totalOrders = totalOrders,
        acceptsMarketing = acceptsMarketing,
        taxExempt = taxExempt
    )
}