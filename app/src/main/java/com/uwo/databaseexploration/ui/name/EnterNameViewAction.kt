package com.uwo.databaseexploration.ui.name

sealed class EnterNameViewAction {
    data class OnUpdateFirstName(val firstName: String) : EnterNameViewAction()
    data class OnUpdateLastName(val lastName: String) : EnterNameViewAction()
    object OnSubmitClicked : EnterNameViewAction()
    object OnBackClicked : EnterNameViewAction()
}