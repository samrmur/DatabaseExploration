package com.uwo.databaseexploration.ui.orders

sealed class EnterOrdersAction {
    object NavigateBack : EnterOrdersAction()
    data class NavigateBackWithQuery(val queryType: OrderQueryType, val numOrders: Int) : EnterOrdersAction()
}