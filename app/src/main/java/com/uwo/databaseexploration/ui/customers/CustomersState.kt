package com.uwo.databaseexploration.ui.customers

import com.uwo.databaseexploration.repository.Customer
import java.text.NumberFormat
import java.util.*

object CurrencyFormatter {
    private val format = NumberFormat.getCurrencyInstance()

    init {
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("USD")
    }

    fun format(amount: Int): String {
        return format.format(amount)
    }
}

sealed class CustomersState {
    object Loading : CustomersState()
    data class Loaded(val customers: List<CustomerItemState>) : CustomersState()
    data class Error(val message: String) : CustomersState()
    object Empty : CustomersState()
}

data class CustomerItemState(
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
)

fun Customer.toItemState(): CustomerItemState {
    return CustomerItemState(
        firstName = firstName,
        lastName = lastName,
        phone = phone,
        company = company,
        streetAddress = streetAddress,
        city = city,
        region = region,
        country = country,
        postalCode = postalCode,
        totalSpent = CurrencyFormatter.format(totalSpent),
        totalOrders = totalOrders
    )
}
