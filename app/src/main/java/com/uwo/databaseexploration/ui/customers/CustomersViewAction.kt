package com.uwo.databaseexploration.ui.customers

import android.net.Uri

sealed class CustomersViewAction {
    object OnImportFileClicked : CustomersViewAction()
    object OnBackClicked : CustomersViewAction()
    object OnRefreshClicked : CustomersViewAction()
    object OnDeleteAllClicked : CustomersViewAction()
    object OnSearchByNameClicked : CustomersViewAction()
    object OnSearchByTotalOrdersClicked : CustomersViewAction()
    data class OnFilePicked(val uri: Uri) : CustomersViewAction()
}