package com.uwo.databaseexploration.ui.customers

import com.uwo.databaseexploration.repository.Customer

sealed class CustomersState {
    object Loading : CustomersState()
    data class Loaded(val loadedState: CustomersLoadedState) : CustomersState()
}

data class CustomersLoadedState(val customers: List<Customer>)
