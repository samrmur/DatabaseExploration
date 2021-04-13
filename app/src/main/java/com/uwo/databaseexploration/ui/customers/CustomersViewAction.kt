package com.uwo.databaseexploration.ui.customers

import android.net.Uri
import com.uwo.databaseexploration.ui.orders.OrderQueryType

sealed class CustomersViewAction {
    object OnImportFileClicked : CustomersViewAction()
    object OnBackClicked : CustomersViewAction()
    object OnRefreshClicked : CustomersViewAction()
    object OnDeleteAllClicked : CustomersViewAction()
    object OnSearchByNameClicked : CustomersViewAction()
    object OnSearchByTotalOrdersClicked : CustomersViewAction()
    data class OnNameQueryReceived(val firstName: String, val lastName: String) : CustomersViewAction()
    data class OnOrdersQueryReceived(val orderType: OrderQueryType, val numOrders: Int) : CustomersViewAction()
    data class OnFilePicked(val uri: Uri) : CustomersViewAction()
}