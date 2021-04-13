package com.uwo.databaseexploration.core

import android.app.Application
import com.uwo.databaseexploration.csv.CustomerCsvReader
import toothpick.config.Module
import toothpick.ktp.binding.bind

class CustomerModule(
    application: Application
) : Module() {
    init {
        bind<CustomerCsvReader>().toInstance(CustomerCsvReader(applicationContext = application.applicationContext))
    }
}