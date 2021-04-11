package com.uwo.databaseexploration.core

import android.app.Application
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module

class DatabaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KTP.openScope(ApplicationScope::class.java)
            .installModules(
                ApplicationModule(application = this),
                DatabaseModule(application = this)
            )
    }
}