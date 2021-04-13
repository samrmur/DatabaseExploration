package com.uwo.databaseexploration.repository.realm

import com.uwo.databaseexploration.repository.Customer
import com.uwo.databaseexploration.repository.CustomerOrdersQueryType
import com.uwo.databaseexploration.repository.CustomerRepository as ICustomerRepository
import com.uwo.databaseexploration.repository.toDomainCustomer
import com.uwo.databaseexploration.repository.toRealmCustomer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val customerDao: CustomerDao
): ICustomerRepository {
    override suspend fun getCustomers(): List<Customer> {
        return customerDao.getAllCustomers().map { customer ->
            customer.toDomainCustomer()
        }
    }

    override suspend fun findByName(firstName: String, lastName: String): List<Customer> {
        return customerDao.getCustomersByName(firstName = firstName, lastName = lastName).map { customer ->
            customer.toDomainCustomer()
        }
    }

    override suspend fun findByOrders(
        queryType: CustomerOrdersQueryType,
        numOrders: Int
    ): List<Customer> {
        val customers = when (queryType) {
            is CustomerOrdersQueryType.EqualTo -> customerDao.getCustomersEqualToOrders(totalOrders = numOrders)
            is CustomerOrdersQueryType.GreaterThan -> customerDao.getCustomersGreaterThanOrders(totalOrders = numOrders)
            is CustomerOrdersQueryType.LessThan -> customerDao.getCustomersLessThanOrders(totalOrders = numOrders)
        }

        return customers.map { customer ->
            customer.toDomainCustomer()
        }
    }

    override suspend fun insertCustomers(customers: List<Customer>) {
        val realmCustomers = customers.map { customer ->
            customer.toRealmCustomer()
        }

        customerDao.insertCustomers(realmCustomers)
    }

    override suspend fun deleteAllCustomers() {
        customerDao.deleteAllCustomers()
    }
}