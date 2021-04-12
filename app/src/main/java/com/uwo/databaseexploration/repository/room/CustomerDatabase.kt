package com.uwo.databaseexploration.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Customer::class], version = 1, exportSchema = false)
abstract class CustomerDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
}