package com.uwo.databaseexploration.ui.customers

sealed class CustomersAction {
    object NavigateBack : CustomersAction()
    object NavigateToNameQueryScreen : CustomersAction()
    object NavigateToTotalOrdersQueryScreen : CustomersAction()
    object OpenCsvFilePicker : CustomersAction()
    data class DisplayError(val message: String) : CustomersAction()
    data class DisplayProfiledOperation(val operationName: String, val totalOperationTime: Long) : CustomersAction()
}
