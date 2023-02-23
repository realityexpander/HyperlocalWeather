package com.realityexpander.weatherhere.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.realityexpander.weatherhere.presentation.ui.theme.DarkBlue
import com.realityexpander.weatherhere.presentation.ui.theme.DeepBlue
import com.realityexpander.weatherhere.presentation.ui.theme.WeatherHereTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

// Based on these designs:
// https://dribbble.com/shots/17998271-Cuacane-Weather-App

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup the permission launcher & callback
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            // After we have permissions, we can get the weather
            viewModel.loadWeatherInfo()
        }

        // Get permissions to access location (opens dialog)
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))

        setContent {
            WeatherHereTheme {

                fun refresh() {
                    viewModel.loadWeatherInfo()
                }
                val pullRefreshState =
                    rememberPullRefreshState(viewModel.state.isLoading, ::refresh)

                LaunchedEffect(key1 = true) {
                    // refresh every 5 minutes
                    while (true) {
                        delay(5 * 60 * 1000)
                        refresh()
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(DarkBlue)
                    ) {
                        item {
                            WeatherCard(
                                state = viewModel.state,
                                backgroundColor = DeepBlue
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            WeatherForecast(state = viewModel.state)
                            Spacer(modifier = Modifier.height(16.dp))

                            viewModel.state.city?.let {
                                Text(
                                    text = "${viewModel.state.city}, ${viewModel.state.country}",
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }

                    viewModel.state.error?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    PullRefreshIndicator(
                        viewModel.state.isLoading,
                        pullRefreshState,
                        Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}