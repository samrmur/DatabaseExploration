package com.uwo.databaseexploration.ui.orders

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusEventModifier
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
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
                is EnterOrdersAction.NavigateBackWithQuery -> {
                    finishWithResult(EnterOrdersContract.RESULT_CODE) {
                        putExtra(EnterOrdersContract.QUERY_TYPE_EXTRA, action.queryType)
                        putExtra(EnterOrdersContract.NUM_ORDERS_EXTRA, action.numOrders)
                    }
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Enter orders query")
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
            }
        ) {
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
                    QueryTypeMenu(
                        modifier = Modifier
                            .weight(10f)
                            .align(Alignment.Bottom),
                        state = state
                    )
                    Spacer(modifier = Modifier.weight(1f))
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
                            Text(text = "Total Orders")
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
    private fun QueryTypeMenu(modifier: Modifier, state: EnterOrdersState) {
        var expanded by remember { mutableStateOf(false) }
        val onItemClick: (onClick: () -> Unit) -> () -> Unit = { onClick ->
            {
                onClick.invoke()
                expanded = false
            }
        }

        OutlinedTextField(
            modifier = modifier.onFocusChanged { focusState ->
                expanded = focusState.isFocused
            },
            value = getNameOfQueryType(queryType = state.queryType),
            onValueChange = {},
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null
                )
            },
            label = {
                Text("Query Type")
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

    @Preview
    @Composable
    private fun EnterOrdersPreview() {
        EnterOrdersView(state = EnterOrdersState(
            queryType = OrderQueryType.EQUAL,
            numOrders = 1000
        ))
    }

    private fun getNameOfQueryType(queryType: OrderQueryType): String {
        return when (queryType) {
            OrderQueryType.EQUAL -> "Equal to"
            OrderQueryType.BIGGER_THAN -> "Bigger than"
            OrderQueryType.LESS_THAN -> "Less than"
        }
    }
}