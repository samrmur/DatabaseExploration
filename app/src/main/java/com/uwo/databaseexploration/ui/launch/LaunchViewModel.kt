package com.uwo.databaseexploration.ui.launch

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uwo.databaseexploration.core.DATABASE_USE_ROOM_FLAG
import com.uwo.databaseexploration.core.setToRealm
import com.uwo.databaseexploration.core.setToRoom
import com.uwo.databaseexploration.core.shouldUseRoom
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ViewModel(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val _action: MutableLiveData<LaunchAction> = MutableLiveData()
    val action: LiveData<LaunchAction>
        get() = _action

    private val _state: MutableLiveData<LaunchState> = MutableLiveData(LaunchState(isUsingRoom = sharedPreferences.shouldUseRoom()))
    val state: LiveData<LaunchState>
        get() = _state

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this@LaunchViewModel)
    }

    fun handleViewAction(action: LaunchViewAction) {
        when (action) {
            is LaunchViewAction.OnDatabaseTypeChanged -> if (action.useRoom) sharedPreferences.setToRoom() else sharedPreferences.setToRealm()
            is LaunchViewAction.OnViewCustomersClicked -> _action.postValue(LaunchAction.NavigateToCustomers)
        }
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences, key: String) {
        if (key == DATABASE_USE_ROOM_FLAG) {
            _state.postValue(LaunchState(isUsingRoom = preferences.getBoolean(key, false)))
        }
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}