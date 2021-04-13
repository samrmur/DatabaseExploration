package com.uwo.databaseexploration.ui.customers

import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uwo.databaseexploration.core.shouldUseRoom
import com.uwo.databaseexploration.csv.CustomerCsvReader
import com.uwo.databaseexploration.repository.Customer
import com.uwo.databaseexploration.repository.profiled.CustomerOperationType
import com.uwo.databaseexploration.repository.profiled.ProfiledCustomerRepository
import com.uwo.databaseexploration.repository.profiled.ProfiledCustomerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CustomersViewModel @Inject constructor(
    private val customerCsvReader: CustomerCsvReader,
    private val sharedPreferences: SharedPreferences,
    private val profiledCustomerRepository: ProfiledCustomerRepository,
): ViewModel() {
    private val _operationState: MutableLiveData<CustomersOperationState> = MutableLiveData(CustomersOperationState.AllCustomers)
    private val _action: MutableLiveData<CustomersAction> = MutableLiveData()
    val action: LiveData<CustomersAction>
        get() = _action

    private val _state: MutableLiveData<CustomersState> = MutableLiveData(CustomersState.Loading)
    val state: LiveData<CustomersState>
        get() = _state

    init {
        viewModelScope.launch(Dispatchers.Default) {
            profiledCustomerRepository.profiledResponse.collect { response ->
                if (response is ProfiledCustomerResponse.Available) {
                    val operationName = when (val operationType = response.operationType) {
                        is CustomerOperationType.GetAllCustomers -> "getAllCustomers()"
                        is CustomerOperationType.GetAllByName -> "getCustomersByName(firstName: ${operationType.firstName}, lastName: ${operationType.lastName})"
                        is CustomerOperationType.InsertCustomers -> "insertCustomers(numRows: ${operationType.numRows})"
                        is CustomerOperationType.DeleteAllCustomers -> "deleteAllCustomers()"
                    }

                    _action.postValue(CustomersAction.DisplayProfiledOperation(
                        operationName = operationName,
                        totalOperationTime = response.timeTaken
                    ))
                }
            }
        }

        getAllCustomers()
    }

    fun getDatabaseType(): String {
        return if (sharedPreferences.shouldUseRoom()) "Room" else "Realm"
    }

    fun handleViewAction(action: CustomersViewAction) {
        when (action) {
            is CustomersViewAction.OnImportFileClicked -> _action.postValue(CustomersAction.OpenCsvFilePicker)
            is CustomersViewAction.OnRefreshClicked -> onRefresh()
            is CustomersViewAction.OnDeleteAllClicked -> deleteAllCustomers()
            is CustomersViewAction.OnBackClicked -> _action.postValue(CustomersAction.NavigateBack)
            is CustomersViewAction.OnSearchByNameClicked -> _action.postValue(CustomersAction.NavigateToNameQueryScreen)
            is CustomersViewAction.OnSearchByTotalOrdersClicked -> _action.postValue(CustomersAction.NavigateToTotalOrdersQueryScreen)
            is CustomersViewAction.OnFilePicked -> handleImport(uri = action.uri)
            is CustomersViewAction.OnNameQueryReceived -> getCustomerByName(firstName = action.firstName, lastName = action.lastName)
            is CustomersViewAction.OnOrdersQueryReceived -> Unit
        }
    }

    private fun handleImport(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(CustomersState.Loading)

            try {
                val customers = customerCsvReader.read(uri = uri)
                profiledCustomerRepository.insertCustomers(customers = customers)
                onRefresh()
            } catch (exception: Exception) {
                _action.postValue(CustomersAction.DisplayError(message = exception.message ?: "An error has occurred!"))
                _state.postValue(CustomersState.Error(message = exception.message ?: "An error has occurred!"))
            }
        }
    }

    private fun onRefresh() {
        when (val state = _operationState.value) {
            is CustomersOperationState.AllCustomers -> getAllCustomers()
            is CustomersOperationState.CustomersByName -> getCustomerByName(firstName = state.firstName, lastName = state.lastName)
        }
    }

    private fun getAllCustomers() {
        viewModelScope.launch(Dispatchers.IO) {
            runCustomersOperation {
                profiledCustomerRepository.getCustomers()
            }
        }
    }

    private fun deleteAllCustomers() {
        viewModelScope.launch(Dispatchers.IO) {
            runCustomersOperation {
                profiledCustomerRepository.deleteAllCustomers()
                profiledCustomerRepository.getCustomers()
            }
        }
    }

    private fun getCustomerByName(firstName: String, lastName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCustomersOperation {
                profiledCustomerRepository.findByName(firstName = firstName, lastName = lastName)
            }
        }
    }

    private suspend fun runCustomersOperation(operation: suspend () -> List<Customer>) {
        _state.postValue(CustomersState.Loading)

        val customerItemStates = operation().map { customer ->
            customer.toItemState()
        }

        val state = if (customerItemStates.isEmpty()) {
            CustomersState.Empty
        } else {
            CustomersState.Loaded(customers = customerItemStates)
        }

        _state.postValue(state)
    }
}