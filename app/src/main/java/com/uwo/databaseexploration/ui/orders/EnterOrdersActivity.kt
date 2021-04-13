package com.uwo.databaseexploration.ui.orders

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.uwo.databaseexploration.core.finishWithResult
import com.uwo.databaseexploration.core.viewmodel.provideActivityViewModel

class EnterOrdersActivity: AppCompatActivity() {
    private val viewModel: EnterOrdersViewModel by lazy {
        provideActivityViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.action.observe(this, { action ->
            when (action) {
                is EnterOrdersAction.NavigateBack -> finish()
                is EnterOrdersAction.NavigateBackWithQuery -> finishWithResult(101) {
                    putExtra("queryType", action.queryType)
                    putExtra("NUM", action.numOrders)
                }
            }
        })

        setContent {
            MaterialTheme {
                EnterOrdersScreen()
            }
        }
    }

    @Composable
    private fun EnterOrdersScreen() {
        val state: EnterOrdersState by viewModel.state.observeAsState(EnterOrdersState())

        EnterOrdersView(state = state)
    }

    @Composable
    private fun EnterOrdersView(state: EnterOrdersState) {
        Column {
            TopAppBar(
                title = {
                    Text(text = "Enter name query")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.handleViewAction(EnterOrdersViewAction.OnBackClicked)
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

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    Box(
                        modifier = Modifier.weight(10F)
                    ) {
                        Text(text = getNameOfQueryType(state.queryType))
                        QueryTypeMenu()
                    }
                    Spacer(Modifier.weight(1f))
                    OutlinedTextField(
                        modifier = Modifier.weight(10F),
                        value = state.numOrders.toString(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { orders ->
                            val numOrders = orders.toIntOrNull()

                            if (numOrders != null) {
                                viewModel.handleViewAction(EnterOrdersViewAction.OnUpdateOrders(numOrders = numOrders))
                            } else {
                                viewModel.handleViewAction(EnterOrdersViewAction.OnUpdateOrders(numOrders = state.numOrders))
                            }
                        },
                        label = {
                            Text(text = "Last Name")
                        }
                    )
                }
                Button(
                    onClick = {
                        viewModel.handleViewAction(EnterOrdersViewAction.OnSubmitClicked)
                    }
                ) {
                    Text("SUBMIT")
                }
            }
        }
    }

    @Composable
    private fun QueryTypeMenu() {
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
                    imageVector = Icons.Filled.ExpandMore,
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
                viewModel.handleViewAction(EnterOrdersViewAction.OnUpdateQueryType(queryType = OrderQueryType.BIGGER_THAN))
            }) {
                Text(getNameOfQueryType(queryType = OrderQueryType.BIGGER_THAN))
            }
            DropdownMenuItem(onClick = onItemClick {
                viewModel.handleViewAction(EnterOrdersViewAction.OnUpdateQueryType(queryType = OrderQueryType.EQUAL))
            }) {
                Text(getNameOfQueryType(queryType = OrderQueryType.EQUAL))
            }
            DropdownMenuItem(onClick = onItemClick {
                viewModel.handleViewAction(EnterOrdersViewAction.OnUpdateQueryType(queryType = OrderQueryType.LESS_THAN))
            }) {
                Text(getNameOfQueryType(queryType = OrderQueryType.LESS_THAN))
            }
        }
    }

    private fun getNameOfQueryType(queryType: OrderQueryType): String {
        return when (queryType) {
            OrderQueryType.EQUAL -> "Equal to"
            OrderQueryType.BIGGER_THAN -> "Bigger than"
            OrderQueryType.LESS_THAN -> "Less than"
        }
    }
}