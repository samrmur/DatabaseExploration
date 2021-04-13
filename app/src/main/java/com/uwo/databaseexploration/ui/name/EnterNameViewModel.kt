package com.uwo.databaseexploration.ui.name

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class EnterNameViewModel @Inject constructor(): ViewModel() {
    private val _action: MutableLiveData<EnterNameAction> = MutableLiveData()
    val action: LiveData<EnterNameAction>
        get() = _action

    private val _state: MutableLiveData<EnterNameState> = MutableLiveData(EnterNameState())
    val state: LiveData<EnterNameState>
        get() = _state

    fun handleViewAction(action: EnterNameViewAction) {
        when (action) {
            is EnterNameViewAction.OnUpdateFirstName -> updateInput(firstName = action.firstName)
            is EnterNameViewAction.OnUpdateLastName -> updateInput(lastName = action.lastName)
            is EnterNameViewAction.OnSubmitClicked -> state.value?.let { state ->
                _action.postValue(EnterNameAction.NavigateBackWithQuery(
                    firstName = state.firstName,
                    lastName = state.lastName
                ))
            }
            is EnterNameViewAction.OnBackClicked -> _action.postValue(EnterNameAction.NavigateBack)
        }
    }

    private fun updateInput(firstName: String? = null, lastName: String? = null) {
        val currentState = _state.value ?: EnterNameState()
        val newState = currentState.copy(
            firstName = firstName ?: currentState.firstName,
            lastName = lastName ?: currentState.lastName
        )

        _state.postValue(newState)
    }
}