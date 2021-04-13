package com.uwo.databaseexploration.repository.profiled

import com.uwo.databaseexploration.repository.Customer
import com.uwo.databaseexploration.repository.CustomerOrdersQueryType
import com.uwo.databaseexploration.repository.CustomerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ProfiledCustomerRepository @Inject constructor(
    private val customerRepository: CustomerRepository
): CustomerRepository {
    private val _profiledResponse: MutableStateFlow<ProfiledCustomerResponse> = MutableStateFlow(ProfiledCustomerResponse.Initialized)
    val profiledResponse: StateFlow<ProfiledCustomerResponse>
        get() = _profiledResponse

    override suspend fun getCustomers(): List<Customer> {
        return runProfiledOperation(
            operation = {
                customerRepository.getCustomers()
            },
            operationType = CustomerOperationType.GetAllCustomers
        )
    }

    override suspend fun findByName(firstName: String, lastName: String): List<Customer> {
        return runProfiledOperation(
            operation = {
                customerRepository.findByName(firstName, lastName)
            },
            operationType = CustomerOperationType.GetAllByName(firstName, lastName)
        )
    }

    override suspend fun findByOrders(
        queryType: CustomerOrdersQueryType,
        numOrders: Int
    ): List<Customer> {
        return runProfiledOperation(
            operation = {
                customerRepository.findByOrders(queryType = queryType, numOrders = numOrders)
            },
            operationType = CustomerOperationType.GetAllByOrders(ordersQueryType = queryType, numOrders = numOrders)
        )
    }

    override suspend fun insertCustomers(customers: List<Customer>) {
        return runProfiledOperation(
            operation = {
                customerRepository.insertCustomers(customers)
            },
            operationType = CustomerOperationType.InsertCustomers(numRows = customers.size)
        )
    }

    override suspend fun deleteAllCustomers() {
        return runProfiledOperation(
            operation = {
                customerRepository.deleteAllCustomers()
            },
            operationType = CustomerOperationType.DeleteAllCustomers
        )
    }

    private suspend fun <Response> runProfiledOperation(operation: suspend () -> Response, operationType: CustomerOperationType): Response {
        val startTime = System.currentTimeMillis()
        val response = operation.invoke()
        val endTime = System.currentTimeMillis()

        _profiledResponse.emit(ProfiledCustomerResponse.Available(
            timeTaken = endTime - startTime,
            operationType = operationType
        ))

        return response
    }
}