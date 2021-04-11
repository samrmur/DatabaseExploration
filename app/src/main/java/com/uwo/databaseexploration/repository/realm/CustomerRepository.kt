package com.uwo.databaseexploration.repository.realm

import com.uwo.databaseexploration.repository.Customer
import com.uwo.databaseexploration.repository.CustomerRepository as ICustomerRepository
import com.uwo.databaseexploration.repository.toDomainCustomer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val customerDao: CustomerDao
): ICustomerRepository {
    override fun getCustomers(): Flow<List<Customer>> {
        return customerDao.getAllCustomers().map { customers ->
            customers.map { customer ->
                customer.toDomainCustomer()
            }
        }
    }

    override fun findByName(firstName: String, lastName: String): Flow<List<Customer>> {
        return customerDao.getCustomersByName(firstName = firstName, lastName = lastName).map { customers ->
            customers.map { customer ->
                customer.toDomainCustomer()
            }
        }
    }
}