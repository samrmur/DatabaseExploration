package com.uwo.databaseexploration.repository.profiled

import com.uwo.databaseexploration.repository.CustomerOrdersQueryType

sealed class CustomerOperationType {
    object GetAllCustomers : CustomerOperationType()
    object DeleteAllCustomers : CustomerOperationType()
    data class InsertCustomers(val numRows: Int) : CustomerOperationType()
    data class GetAllByName(val firstName: String, val lastName: String) : CustomerOperationType()
    data class GetAllByOrders(val ordersQueryType: CustomerOrdersQueryType, val numOrders: Int) : CustomerOperationType()
}
