package com.uwo.databaseexploration.ui.name

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uwo.databaseexploration.core.finishWithResult
import com.uwo.databaseexploration.core.viewmodel.provideActivityViewModel

class EnterNameActivity: AppCompatActivity() {
    private val viewModel: EnterNameViewModel by lazy {
        provideActivityViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.action.observe(this, { action ->
            when (action) {
                is EnterNameAction.NavigateBack -> finish()
                is EnterNameAction.NavigateBackWithQuery -> finishWithResult(101) {
                    putExtra("FIRST", action.firstName)
                    putExtra("LAST", action.lastName)
                }
            }
        })

        setContent {
            MaterialTheme {
                EnterNameScreen()
            }
        }
    }

    @Composable
    private fun EnterNameScreen() {
        val state: EnterNameState by viewModel.state.observeAsState(EnterNameState())

        EnterNameView(state = state)
    }

    @Composable
    private fun EnterNameView(state: EnterNameState) {
        Column {
            TopAppBar(
                title = {
                    Text(text = "Enter name query")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.handleViewAction(EnterNameViewAction.OnBackClicked)
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
                    OutlinedTextField(
                        modifier = Modifier.weight(10F),
                        value = state.firstName,
                        onValueChange = { firstName ->
                            viewModel.handleViewAction(EnterNameViewAction.OnUpdateFirstName(firstName = firstName))
                        },
                        label = {
                            Text(text = "First Name")
                        }
                    )
                    Spacer(Modifier.weight(1f))
                    OutlinedTextField(
                        modifier = Modifier.weight(10F),
                        value = state.lastName,
                        onValueChange = { lastName ->
                            viewModel.handleViewAction(EnterNameViewAction.OnUpdateLastName(lastName = lastName))
                        },
                        label = {
                            Text(text = "Last Name")
                        }
                    )
                }
                Button(
                    onClick = {
                        viewModel.handleViewAction(EnterNameViewAction.OnSubmitClicked)
                    }
                ) {
                    Text("SUBMIT")
                }
            }
        }
    }

    @Preview
    @Composable
    private fun EnterNamePreview() {
        EnterNameView(state = EnterNameState(
            firstName = "Samer",
            lastName = "Alabi"
        ))
    }
}