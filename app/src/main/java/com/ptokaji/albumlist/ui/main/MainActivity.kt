package com.ptokaji.albumlist.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.ptokaji.albumlist.ui.screen.ContentScreen
import com.ptokaji.albumlist.ui.screen.ErrorScreen
import com.ptokaji.albumlist.ui.screen.LoadingScreen
import com.ptokaji.albumlist.ui.theme.AlbumListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initScreen()
        setContent {
            AlbumListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainViewScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainViewScreen(viewModel: MainViewModel) {
    val uiState: MainUiState? by viewModel.uiState.observeAsState()
    when (uiState) {
        is MainUiState.Content -> {
            ContentScreen((uiState as MainUiState.Content).uiList)
        }
        is MainUiState.Error -> ErrorScreen((uiState as MainUiState.Error).error)
        MainUiState.Loading -> LoadingScreen()
        null -> ErrorScreen((uiState as MainUiState.Error).error)
    }
}