package com.uwo.databaseexploration.ui.customers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uwo.databaseexploration.repository.profiled.CustomerOperationType
import com.uwo.databaseexploration.repository.profiled.ProfiledCustomerRepository
import com.uwo.databaseexploration.repository.profiled.ProfiledCustomerResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CustomersViewModel @Inject constructor(
    private val profiledCustomerRepository: ProfiledCustomerRepository,
): ViewModel() {
    private val _action: MutableLiveData<CustomersAction> = MutableLiveData()
    val action: LiveData<CustomersAction>
        get() = _action

    private val _state: MutableLiveData<CustomersState> = MutableLiveData(CustomersState.Loading)
    val state: LiveData<CustomersState>
        get() = _state

    init {
        viewModelScope.launch {
            profiledCustomerRepository.profiledResponse.collect { response ->
                if (response is ProfiledCustomerResponse.Available) {
                    val operationName = when (val operationType = response.operationType) {
                        is CustomerOperationType.GetAllCustomers -> "getAllCustomers()"
                        is CustomerOperationType.GetAllByName -> "getCustomersByName(firstName: ${operationType.firstName}, lastName: ${operationType.lastName})"
                    }

                    _action.postValue(CustomersAction.DisplayProfiledOperation(
                        operationName = operationName,
                        totalOperationTime = response.timeTaken
                    ))
                }
            }
        }

        viewModelScope.launch {
            val loadedState = CustomersLoadedState(customers = profiledCustomerRepository.getCustomers())
            _state.postValue(CustomersState.Loaded(loadedState = loadedState))
        }
    }

    fun handleViewAction(action: CustomersViewAction) {
        when (action) {
            is CustomersViewAction.ImportFileClicked -> Unit
        }
    }
}