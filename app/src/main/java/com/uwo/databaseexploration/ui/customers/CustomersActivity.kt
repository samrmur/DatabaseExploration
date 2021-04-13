package com.uwo.databaseexploration.ui.customers

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uwo.databaseexploration.core.viewmodel.provideActivityViewModel
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class CustomersActivity: AppCompatActivity() {
    private val viewModel: CustomersViewModel by lazy {
        provideActivityViewModel()
    }

    private val launchGetContent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { validUri ->
            Log.d(this::class.java.simpleName, "Sending action")
            viewModel.handleViewAction(CustomersViewAction.OnFilePicked(uri = validUri))
        }
    }

    private val snackBarHostState = SnackbarHostState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.action.observe(this, { action ->
            when (action) {
                is CustomersAction.DisplayProfiledOperation -> showSnackBar(
                    message = "${action.operationName} took ${action.totalOperationTime} ms"
                )
                is CustomersAction.DisplayError -> showSnackBar(
                    message = "Error: ${action.message}"
                )
                is CustomersAction.NavigateToNameQueryScreen -> Unit
                is CustomersAction.NavigateToTotalOrdersQueryScreen -> Unit
                is CustomersAction.NavigateBack -> finish()
                is CustomersAction.OpenCsvFilePicker -> launchGetContent.launch("*/*")
            }
        })

        setContent {
            MaterialTheme {
                CustomersScreen()
            }
        }
    }

    private fun showSnackBar(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        lifecycleScope.launch {
            snackBarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long
            )
        }
    }

    @Composable
    private fun CustomersScreen() {
        val state: CustomersState by viewModel.state.observeAsState(CustomersState.Loading)

        CustomersView(state, viewModel.getDatabaseType())
    }

    @Composable
    private fun CustomersView(state: CustomersState, databaseType: String) {
        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth()
        ) {
            TopAppBar(
                title = {
                    Text(text = "Customers in $databaseType context")
                },
                actions = {
                    CustomerOverflowMenu()
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.handleViewAction(CustomersViewAction.OnBackClicked)
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    )
                }
            )

            when (state) {
                is CustomersState.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is CustomersState.Empty -> {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "No customers have been found!", fontSize = 16.sp)
                    }
                }
                is CustomersState.Loaded -> {
                    val customers = state.customers

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        items(customers.size) { position ->
                            CustomerItem(customers[position])
                        }
                    }
                }
                is CustomersState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "An error has occurred, \"${state.message}\", please refresh in the menu!",
                            fontSize = 16.sp
                        )
                    }
                }
            }

            ProfiledSnackBar()
        }
    }

    @Composable
    private fun CustomerOverflowMenu() {
        var expanded by remember { mutableStateOf(false) }
        val onItemClick: (onClick: () -> Unit) -> () -> Unit = { onClick ->
            {
                onClick.invoke()
                expanded = false
            }
        }

        IconButton(
            content = {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null
                )
            },
            onClick = {
                expanded = true
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            DropdownMenuItem(onClick = onItemClick {
                viewModel.handleViewAction(CustomersViewAction.OnRefreshClicked)
            }) {
                Text("Refresh")
            }
            DropdownMenuItem(onClick = onItemClick {
                viewModel.handleViewAction(CustomersViewAction.OnDeleteAllClicked)
            }) {
                Text("Delete all")
            }
            Divider()
            DropdownMenuItem(onClick = onItemClick {
                viewModel.handleViewAction(CustomersViewAction.OnImportFileClicked)
            }) {
                Text("Import CSV file")
            }
            Divider()
            DropdownMenuItem(onClick = onItemClick {
                viewModel.handleViewAction(CustomersViewAction.OnSearchByNameClicked)
            }) {
                Text("Search by name")
            }
            DropdownMenuItem(onClick = onItemClick {
                viewModel.handleViewAction(CustomersViewAction.OnSearchByTotalOrdersClicked)
            }) {
                Text("Search by total orders")
            }
        }
    }

    @Composable
    private fun CustomerItem(customer: CustomerItemState) {
        Column(modifier = Modifier.padding(all = 10.dp)) {
            Text(
                text = "${customer.firstName} ${customer.lastName}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = customer.phone,
                fontSize = 14.sp
            )
            Text(
                text = customer.company,
                fontSize = 14.sp
            )
            Text(
                text = customer.streetAddress,
                fontSize = 14.sp
            )
            Text(
                text = "${customer.city}, ${customer.region}, ${customer.country}  ${customer.postalCode}",
                fontSize = 14.sp
            )
            Text(
                text = "Total Orders: ${customer.totalOrders}",
                fontSize = 14.sp
            )
            Text(
                text = "Total Spent: ${customer.totalSpent}",
                fontSize = 14.sp
            )
        }
        Divider()
    }

    @Composable
    private fun ProfiledSnackBar() {
        val hostState by remember { mutableStateOf(snackBarHostState) }

        SnackbarHost(
            hostState = hostState,
            snackbar = { snackBarData ->
                Snackbar(snackbarData = snackBarData)
            }
        )
    }

    @Preview
    @Composable
    private fun LoadingPreview() {
        CustomersView(state = CustomersState.Loading, databaseType = "Customers in Room context")
    }

    @Preview
    @Composable
    private fun LoadedPreview() {
        CustomersView(
            state = CustomersState.Loaded(listOf(
                CustomerItemState(
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
                    totalSpent = "$1,000.00",
                )
            )),
            databaseType = "Customers in Room context"
        )
    }
}