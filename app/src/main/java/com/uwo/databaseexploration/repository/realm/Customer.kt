package com.uwo.databaseexploration.repository.realm

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

open class Customer(
    @PrimaryKey var id: String = "",
    @Index var firstName: String = "",
    @Index var lastName: String = "",
    var phone: String = "",
    var company: String = "",
    var streetAddress: String = "",
    var city: String = "",
    var region: String = "",
    var country: String = "",
    var postalCode: String = "",
    var totalSpent: Int = 0,
    @Index var totalOrders: Int = 0,
    var acceptsMarketing: Boolean = false,
    var taxExempt: Boolean = false
) : RealmObject()