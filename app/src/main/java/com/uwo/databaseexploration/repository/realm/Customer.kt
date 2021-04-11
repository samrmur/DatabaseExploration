package com.uwo.databaseexploration.repository.realm

import io.realm.RealmObject

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
): RealmObject()