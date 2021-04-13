package com.uwo.databaseexploration.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

private const val FIRST_NAME_COLUMN = "first_name"
private const val LAST_NAME_COLUMN = "last_name"
private const val TOTAL_ORDERS_COLUMN = "total_orders"

@Entity(indices = [
    Index(name = "NameIndex", value = [FIRST_NAME_COLUMN, LAST_NAME_COLUMN]),
    Index(name = "TotalOrdersIndex", value = [TOTAL_ORDERS_COLUMN])
])
data class Customer(
    @PrimaryKey val id: String,
    @ColumnInfo(name = FIRST_NAME_COLUMN) val firstName: String,
    @ColumnInfo(name = LAST_NAME_COLUMN) val lastName: String,
    @ColumnInfo val phone: String,
    @ColumnInfo val company: String,
    @ColumnInfo(name = "street_address") val streetAddress: String,
    @ColumnInfo val city: String,
    @ColumnInfo val region: String,
    @ColumnInfo val country: String,
    @ColumnInfo(name = "postal_code")  val postalCode: String,
    @ColumnInfo(name = "total_spent") val totalSpent: Int,
    @ColumnInfo(name = TOTAL_ORDERS_COLUMN) val totalOrders: Int,
    @ColumnInfo(name = "accepts_marketing") val acceptsMarketing: Boolean,
    @ColumnInfo(name = "tax_exempt") val taxExempt: Boolean
)