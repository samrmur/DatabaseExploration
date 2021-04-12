package com.uwo.databaseexploration.ui.customers

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uwo.databaseexploration.core.scopes.ApplicationScope
import com.uwo.databaseexploration.core.scopes.ViewModelScope
import com.uwo.databaseexploration.repository.Customer
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import toothpick.smoothie.viewmodel.installViewModelBinding

class CustomersActivity: AppCompatActivity() {
    private val viewModel: CustomersViewModel by inject<CustomersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()

        setContent {
            MaterialTheme {
                CustomersScreen()
            }
        }
    }

    private fun injectDependencies() {
        KTP.openScope(ApplicationScope::class.java)
            .openSubScope(ViewModelScope::class.java) { scope ->
                scope.installViewModelBinding<CustomersViewModel>(activity = this)
                    .closeOnViewModelCleared(activity = this)
            }
    }

    @Composable
    private fun CustomersScreen() {
        val state: CustomersState by viewModel.state.observeAsState(CustomersState.Loading)

        CustomersView(state)
    }

    @Composable
    private fun CustomersView(state: CustomersState) {
        Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
            TopAppBar(title = {
                Text(text = "Customers")
            })

            when (state) {
                is CustomersState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is CustomersState.Loaded -> {
                    val customers = state.loadedState.customers

                    LazyColumn(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                        items(customers.size) { position ->
                            CustomerItem(customers[position])
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CustomerItem(customer: Customer) {
        Text(text = "${customer.firstName} ${customer.lastName}")
    }

    @Preview
    @Composable
    private fun LoadingPreview() {
        CustomersView(CustomersState.Loading)
    }

    @Preview
    @Composable
    private fun LoadedPreview() {
        CustomersView(CustomersState.Loaded(CustomersLoadedState(listOf(
            Customer(
                id = "1",
                firstName = "Samer",
                lastName = "Alabi",
                streetAddress = "123 Alphabet Street",
                company = "Shopify Inc.",
                phone = "+1 (226) 123 4567",
                city = "London",
                region = "Ontario",
                country = "Canada",
                postalCode = "A1B 2C3",
                totalOrders = 100,
                totalSpent = 1000,
                acceptsMarketing = true,
                taxExempt = false
            )
        ))))
    }
}