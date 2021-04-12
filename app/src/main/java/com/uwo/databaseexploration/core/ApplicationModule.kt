package com.uwo.databaseexploration.core

import android.app.Application
import android.content.SharedPreferences
import toothpick.config.Module
import toothpick.ktp.binding.bind

class ApplicationModule(
    application: DatabaseApplication
): Module() {
    companion object {
        private const val SHARED_PREFERENCES_NAME = "cs4411_shared_prefs"
    }

    init {
        bind<Application>().toInstance(application)
        bind<SharedPreferences>().toInstance(application.getSharedPreferences(SHARED_PREFERENCES_NAME, 0))
    }
}