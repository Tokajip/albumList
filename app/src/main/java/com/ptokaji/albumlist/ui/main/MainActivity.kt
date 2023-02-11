package com.ptokaji.albumlist.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ptokaji.albumlist.data.network.UsersApi
import com.ptokaji.albumlist.ui.theme.AlbumListTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var usersApi: UsersApi

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initScreen()
        viewModel.uiState.observe(this) {state ->
            when(state) {
                is MainUiState.Content -> state.uiList.forEach { Log.d(javaClass.simpleName, it.toString()) }
                MainUiState.Error -> Log.d(javaClass.simpleName, "Error")
                MainUiState.Loading -> Log.d(javaClass.simpleName, "Loading")
            }

        }
        setContent {
            AlbumListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AlbumListTheme {
        Greeting("Android")
    }
}