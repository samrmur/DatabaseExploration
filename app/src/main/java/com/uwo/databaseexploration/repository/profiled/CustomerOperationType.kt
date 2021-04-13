package com.uwo.databaseexploration.repository.profiled

sealed class CustomerOperationType {
    object GetAllCustomers : CustomerOperationType()
    object DeleteAllCustomers : CustomerOperationType()
    data class InsertCustomers(val numRows: Int) : CustomerOperationType()
    data class GetAllByName(val firstName: String, val lastName: String) : CustomerOperationType()
}
