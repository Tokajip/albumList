package com.ptokaji.albumlist.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ptokaji.albumlist.data.datasource.UsersRepository
import com.ptokaji.albumlist.ui.main.MainUiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val disposeBag = CompositeDisposable()

    private val _uiState = MutableLiveData<MainUiState>(Loading)
    val uiState: LiveData<MainUiState> get() = _uiState

    fun initScreen() {
        usersRepository.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _uiState.value = Content(it.map { AlbumUiItem(it.username) })
            }, {
                _uiState.value = Error(it.message ?: "")
            })
            .apply { disposeBag.add(this) }
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}

sealed class MainUiState {
    object Loading : MainUiState()
    data class Error(val error: String) : MainUiState()
    data class Content(var uiList: List<AlbumUiItem>) : MainUiState()
}

data class AlbumUiItem(
    val username: String
)