package com.uwo.databaseexploration.core

import android.app.Application
import com.uwo.databaseexploration.core.scopes.ApplicationScope
import toothpick.ktp.KTP

class DatabaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KTP.openScope(ApplicationScope::class.java)
            .installModules(
                ApplicationModule(application = this),
                DatabaseModule(application = this),
                CustomerModule(application = this)
            )
    }
}