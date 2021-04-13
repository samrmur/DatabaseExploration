package com.uwo.databaseexploration.ui.orders

sealed class EnterOrdersViewAction {
    data class OnUpdateQueryType(val queryType: OrderQueryType) : EnterOrdersViewAction()
    data class OnUpdateOrders(val numOrders: Int) : EnterOrdersViewAction()
    object OnSubmitClicked : EnterOrdersViewAction()
    object OnBackClicked : EnterOrdersViewAction()
}