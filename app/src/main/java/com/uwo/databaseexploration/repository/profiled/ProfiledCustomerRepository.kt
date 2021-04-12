package com.uwo.databaseexploration.repository.profiled

import com.uwo.databaseexploration.repository.Customer
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
        val startTime = System.currentTimeMillis()
        val results = customerRepository.getCustomers()
        val endTime = System.currentTimeMillis()

        _profiledResponse.emit(ProfiledCustomerResponse.Available(
            timeTaken = endTime - startTime,
            operationType = CustomerOperationType.GetAllCustomers
        ))

        return results
    }

    override suspend fun findByName(firstName: String, lastName: String): List<Customer> {
        val startTime = System.currentTimeMillis()
        val results = customerRepository.findByName(firstName, lastName)
        val endTime = System.currentTimeMillis()

        _profiledResponse.emit(ProfiledCustomerResponse.Available(
            timeTaken = endTime - startTime,
            operationType = CustomerOperationType.GetAllByName(firstName, lastName)
        ))

        return results
    }
}