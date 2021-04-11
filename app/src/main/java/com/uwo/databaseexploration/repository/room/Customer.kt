package com.uwo.databaseexploration.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [
    Index()
])
data class Customer(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo val phone: String,
    @ColumnInfo val company: String,
    @ColumnInfo(name = "street_address") val streetAddress: String,
    @ColumnInfo val city: String,
    @ColumnInfo val region: String,
    @ColumnInfo val country: String,
    @ColumnInfo(name = "postal_code")  val postalCode: String,
    @ColumnInfo(name = "total_spent") val totalSpent: String,
    @ColumnInfo(name = "total_orders") val totalOrders: Int,
    @ColumnInfo(name = "accepts_marketing") val acceptsMarketing: Boolean,
    @ColumnInfo(name = "tax_exempt") val taxExempt: Boolean
)