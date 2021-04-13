package com.uwo.databaseexploration.ui.name

sealed class EnterNameAction {
    object NavigateBack : EnterNameAction()
    data class NavigateBackWithQuery(val firstName: String, val lastName: String) : EnterNameAction()
}