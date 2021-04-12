package com.uwo.databaseexploration.ui.customers

sealed class CustomersViewAction {
    object ImportFileClicked : CustomersViewAction()
}