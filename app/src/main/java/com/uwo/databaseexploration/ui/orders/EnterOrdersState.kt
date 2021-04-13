package com.uwo.databaseexploration.ui.orders

enum class OrderQueryType {
    BIGGER_THAN,
    EQUAL,
    LESS_THAN
}

data class EnterOrdersState(
    val queryType: OrderQueryType = OrderQueryType.EQUAL,
    val numOrders: Int = 0
)