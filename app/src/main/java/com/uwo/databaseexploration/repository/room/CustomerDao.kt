package com.uwo.databaseexploration.repository.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customer")
    fun getAll(): List<Customer>

    @Query("SELECT * FROM customer WHERE first_name LIKE :firstName AND last_name LIKE :lastName")
    fun findByName(firstName: String, lastName: String): List<Customer>

    @Query("SELECT * FROM customer WHERE total_orders > :totalOrders")
    fun findGreaterThan(totalOrders: Int): List<Customer>

    @Query("SELECT * FROM customer WHERE total_orders = :totalOrders")
    fun findEqualTo(totalOrders: Int): List<Customer>

    @Query("SELECT * FROM customer WHERE total_orders < :totalOrders")
    fun findLessThan(totalOrders: Int): List<Customer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(customers: List<Customer>)

    @Query("DELETE FROM customer")
    fun deleteAll()
}