package com.uwo.databaseexploration.repository.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customer")
    fun getAll(): List<Customer>

    @Query("SELECT * FROM customer WHERE first_name LIKE :firstName AND last_name LIKE :lastName")
    fun findByName(firstName: String, lastName: String): List<Customer>
}