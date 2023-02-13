package com.ptokaji.albumlist.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ptokaji.albumlist.ui.main.model.AlbumUiItem

@Composable
fun ContentScreen(albumList: List<AlbumUiItem>) {
    LazyColumn {
        items(albumList) {
            Card(
                modifier = Modifier
                    .defaultMinSize(minHeight = 140.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier.padding(8.dp),
                        model = it.thumbnail,
                        contentDescription = "Album thumbnail image"
                    )
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = it.photoTitle,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = it.albumTitle
                        )
                        Text(
                            text = it.username,
                            color = Color.Gray,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }


        }
    }
}