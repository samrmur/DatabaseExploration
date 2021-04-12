package com.uwo.databaseexploration.repository.profiled

sealed class CustomerOperationType {
    object GetAllCustomers : CustomerOperationType()
    data class GetAllByName(val firstName: String, val lastName: String) : CustomerOperationType()
}
