package com.uwo.databaseexploration.repository.profiled

sealed class ProfiledCustomerResponse {
    object Initialized : ProfiledCustomerResponse()
    data class Available(
        val timeTaken: Long,
        val operationType: CustomerOperationType
        ) : ProfiledCustomerResponse()
}