package com.uwo.databaseexploration.ui.customers

sealed class CustomersOperationState {
    object AllCustomers : CustomersOperationState()
    data class CustomersByName(val firstName: String, val lastName: String) : CustomersOperationState()
}