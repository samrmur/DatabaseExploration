package com.uwo.databaseexploration.core

import android.app.Application
import toothpick.config.Module
import toothpick.ktp.binding.bind

class ApplicationModule(
    application: DatabaseApplication
): Module() {
    init {
        bind<Application>().toInstance { application }
    }
}