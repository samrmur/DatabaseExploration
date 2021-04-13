package com.uwo.databaseexploration.csv

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import com.uwo.databaseexploration.repository.Customer
import java.io.*
import javax.inject.Inject

class CustomerCsvReader @Inject constructor(
    private val applicationContext: Context
) {
    companion object {
        private const val SPLIT_ON_COMMA_WHEN_EVEN_QUOTES = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"
    }

    fun read(uri: Uri): List<Customer> {
        val customers = mutableListOf<Customer>()
        val reader = getBufferedReader(uri = uri)

        try {
            // Skip header
            reader.readLine()

            // Read first line
            var nextLine: String? = reader.readLine()

            // Read until empty
            while (nextLine != null) {
                val splitLine = nextLine.split(Regex(SPLIT_ON_COMMA_WHEN_EVEN_QUOTES))

                customers.add(Customer(
                    id = removeQuotes(splitLine[0]),
                    firstName = removeQuotes(splitLine[1]),
                    lastName = removeQuotes(splitLine[2]),
                    phone = removeQuotes(splitLine[3]),
                    company = removeQuotes(splitLine[4]),
                    streetAddress = removeQuotes(splitLine[5]),
                    city = removeQuotes(splitLine[6]),
                    region = removeQuotes(splitLine[7]),
                    country = removeQuotes(splitLine[8]),
                    postalCode = removeQuotes(splitLine[9]),
                    totalSpent = splitLine[10].toInt(),
                    totalOrders = splitLine[11].toInt(),
                    acceptsMarketing = splitLine[12].toBoolean(),
                    taxExempt = splitLine[13].toBoolean(),
                ))

                nextLine = reader.readLine()
            }

            reader.close()

            return customers
        } catch (exception: Exception) {
            reader.close()
            throw Exception("File with path $uri is not properly formatted!")
        }
    }

    private fun getBufferedReader(uri: Uri): BufferedReader {
        try {
            val inputStream = applicationContext.contentResolver.openInputStream(uri)
            val inputStreamReader = InputStreamReader(inputStream)
            return BufferedReader(inputStreamReader)
        } catch (exception: IOException) {
            throw Exception("Could not read file with path $uri!")
        }
    }

    private fun removeQuotes(element: String): String {
        return element.removePrefix(prefix = "\"").removeSuffix("\"")
    }
}