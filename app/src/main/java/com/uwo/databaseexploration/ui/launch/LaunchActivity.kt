package com.uwo.databaseexploration.ui.launch

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uwo.databaseexploration.core.scopes.ApplicationScope
import com.uwo.databaseexploration.core.scopes.ViewModelScope
import com.uwo.databaseexploration.core.viewmodel.InjectedViewModelProvider
import com.uwo.databaseexploration.core.viewmodel.provideActivityViewModel
import com.uwo.databaseexploration.ui.customers.CustomersActivity
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import toothpick.smoothie.viewmodel.installViewModelBinding
import javax.inject.Inject

class LaunchActivity: AppCompatActivity() {
    private val viewModel: LaunchViewModel by lazy {
        provideActivityViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.action.observe(this, { action ->
            when (action) {
                is LaunchAction.NavigateToCustomers -> startActivity(Intent(this, CustomersActivity::class.java))
            }
        })

        setContent {
            MaterialTheme {
                LaunchScreen()
            }
        }
    }

    @Composable
    private fun LaunchScreen() {
        val state: LaunchState by viewModel.state.observeAsState(LaunchState(isUsingRoom = false))

        LaunchView(state.isUsingRoom)
    }

    @Composable
    private fun LaunchView(isUsingRoom: Boolean) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = "Pick database to use")
                })
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 25.dp)) {
                    DatabaseOption(
                        modifier = Modifier.weight(1F),
                        text = "Realm"
                    )
                    Switch(
                        modifier = Modifier.weight(1F),
                        checked = isUsingRoom,
                        onCheckedChange = { check ->
                            viewModel.handleViewAction(LaunchViewAction.OnDatabaseTypeChanged(useRoom = check))
                        },
                    )
                    DatabaseOption(
                        modifier = Modifier.weight(1F),
                        text = "Room"
                    )
                }
                Button(onClick = {
                    viewModel.handleViewAction(LaunchViewAction.OnViewCustomersClicked)
                }) {
                    Text(text = "VIEW CUSTOMERS")
                }
            }
        }
    }

    @Composable
    private fun DatabaseOption(modifier: Modifier, text: String) {
        Text(
            modifier = modifier,
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }

    @Preview
    @Composable
    private fun LaunchPreview() {
        LaunchView(isUsingRoom = true)
    }
}