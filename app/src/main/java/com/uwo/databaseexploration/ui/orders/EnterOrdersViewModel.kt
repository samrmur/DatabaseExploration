package com.uwo.databaseexploration.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class EnterOrdersViewModel @Inject constructor(): ViewModel() {
    private val _action: MutableLiveData<EnterOrdersAction> = MutableLiveData()
    val action: LiveData<EnterOrdersAction>
        get() = _action

    private val _state: MutableLiveData<EnterOrdersState> = MutableLiveData(EnterOrdersState())
    val state: LiveData<EnterOrdersState>
        get() = _state

    fun handleViewAction(action: EnterOrdersViewAction) {
        when (action) {
            is EnterOrdersViewAction.OnUpdateQueryType -> updateInput(queryType = action.queryType)
            is EnterOrdersViewAction.OnUpdateOrders -> updateInput(numOrders = action.numOrders)
            is EnterOrdersViewAction.OnSubmitClicked -> state.value?.let { state ->
                _action.postValue(EnterOrdersAction.NavigateBackWithQuery(
                    queryType = state.queryType,
                    numOrders = state.numOrders
                ))
            }
            is EnterOrdersViewAction.OnBackClicked -> _action.postValue(EnterOrdersAction.NavigateBack)
        }
    }

    private fun updateInput(queryType: OrderQueryType? = null, numOrders: Int? = null) {
        val currentState = _state.value ?: EnterOrdersState()
        val newState = currentState.copy(
            queryType = queryType ?: currentState.queryType,
            numOrders = numOrders ?: currentState.numOrders
        )

        _state.postValue(newState)
    }
}