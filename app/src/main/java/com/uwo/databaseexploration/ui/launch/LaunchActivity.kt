package com.uwo.databaseexploration.ui.launch

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uwo.databaseexploration.core.scopes.ApplicationScope
import com.uwo.databaseexploration.core.scopes.ViewModelScope
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import toothpick.smoothie.viewmodel.installViewModelBinding

class LaunchActivity: AppCompatActivity() {
    private val viewModel: LaunchViewModel by inject<LaunchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        injectDependencies()

        setContent {
            MaterialTheme {
                LaunchScreen()
            }
        }
    }

    private fun injectDependencies() {
        KTP.openScope(ApplicationScope::class.java)
            .openSubScope(ViewModelScope::class.java) { scope ->
                scope.installViewModelBinding<LaunchViewModel>(activity = this)
                    .closeOnViewModelCleared(activity = this)
            }
    }

    @Composable
    private fun LaunchScreen() {
        val state: LaunchState by viewModel.state.observeAsState(LaunchState(isUsingRoom = false))

        LaunchView(state.isUsingRoom)
    }

    @Composable
    private fun LaunchView(isUsingRoom: Boolean) {
        Column {
            TopAppBar(title = {
                Text(text = "Pick database to use")
            })

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp)) {
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
            color = Color.White,
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