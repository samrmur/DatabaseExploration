package com.uwo.databaseexploration.ui.launch

sealed class LaunchViewAction {
    data class OnDatabaseTypeChanged(val useRoom: Boolean) : LaunchViewAction()
    object OnViewCustomersClicked : LaunchViewAction()
}