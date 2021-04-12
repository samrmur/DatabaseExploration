package com.uwo.databaseexploration.ui.customers

sealed class CustomersAction {
    data class DisplayProfiledOperation(val operationName: String, val totalOperationTime: Long) : CustomersAction()
}
