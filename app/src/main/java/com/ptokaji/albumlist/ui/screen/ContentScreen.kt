package com.ptokaji.albumlist.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ptokaji.albumlist.ui.main.AlbumUiItem

@Composable
fun ContentScreen(albumList: List<AlbumUiItem>) {
    LazyColumn {
        items(albumList) {
            Text(text = it.username)
        }
    }
}