package com.ptokaji.albumlist.ui.main.model

sealed class MainUiState {
    object Loading : MainUiState()
    data class Error(val error: String) : MainUiState()
    data class Content(var uiList: List<AlbumUiItem>) : MainUiState()
}
