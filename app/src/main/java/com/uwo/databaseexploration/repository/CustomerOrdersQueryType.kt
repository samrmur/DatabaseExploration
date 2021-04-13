package com.uwo.databaseexploration.repository

sealed class CustomerOrdersQueryType {
    object LessThan : CustomerOrdersQueryType()
    object EqualTo : CustomerOrdersQueryType()
    object GreaterThan : CustomerOrdersQueryType()
}
