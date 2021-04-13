package com.uwo.databaseexploration.repository.room

import com.uwo.databaseexploration.repository.Customer
import com.uwo.databaseexploration.repository.CustomerRepository as ICustomerRepository
import com.uwo.databaseexploration.repository.toDomainCustomer
import com.uwo.databaseexploration.repository.toRoomCustomer
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val customerDao: CustomerDao
): ICustomerRepository {
    override suspend fun getCustomers(): List<Customer> {
        return customerDao.getAll().map { customer ->
            customer.toDomainCustomer()
        }
    }

    override suspend fun findByName(firstName: String, lastName: String): List<Customer> {
        return customerDao.findByName(firstName = firstName, lastName = lastName).map { customer ->
            customer.toDomainCustomer()
        }
    }

    override suspend fun insertCustomers(customers: List<Customer>) {
        val roomCustomers = customers.map { customer ->
            customer.toRoomCustomer()
        }

        customerDao.insert(roomCustomers)
    }

    override suspend fun deleteAllCustomers() {
        customerDao.deleteAll()
    }
}
