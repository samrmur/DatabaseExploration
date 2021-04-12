package com.uwo.databaseexploration.core.viewmodel

import androidx.annotation.MainThread
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uwo.databaseexploration.core.scopes.ApplicationScope
import com.uwo.databaseexploration.core.scopes.ViewModelScope
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import toothpick.smoothie.viewmodel.installViewModelBinding

class InjectedViewModelProvider(private val scope: Scope) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return scope.getInstance(modelClass)
    }
}

@MainThread
inline fun <reified VM : ViewModel> FragmentActivity.provideActivityViewModel(): VM {
    val scope = KTP.openScope(ApplicationScope::class.java)
        .openSubScope(ViewModelScope::class.java) { scope ->
            scope.installViewModelBinding<VM>(this, InjectedViewModelProvider(scope))
                .closeOnViewModelCleared(this)
        }

    return scope.getInstance(VM::class.java)
}