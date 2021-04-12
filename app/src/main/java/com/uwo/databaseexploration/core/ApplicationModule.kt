package com.uwo.databaseexploration.core

import android.app.Application
import android.content.SharedPreferences
import toothpick.config.Module
import toothpick.ktp.binding.bind

class ApplicationModule(
    application: DatabaseApplication
): Module() {
    init {
        bind<Application>().toInstance(application)
        bind<SharedPreferences>().toInstance(application.getSharedPreferences(DATABASE_PREFERENCES_FILE, 0))
    }
}