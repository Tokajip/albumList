package com.ptokaji.albumlist.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ptokaji.albumlist.domain.usecase.AlbumListUseCase
import com.ptokaji.albumlist.ui.main.model.AlbumUiItem
import com.ptokaji.albumlist.ui.main.model.MainUiState
import com.ptokaji.albumlist.ui.main.model.MainUiState.Error
import com.ptokaji.albumlist.ui.main.model.MainUiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val albumListUseCase: AlbumListUseCase,
    private val disposeBag: CompositeDisposable
) : ViewModel() {

    private val _uiState = MutableLiveData<MainUiState>(Loading)
    val uiState: LiveData<MainUiState> get() = _uiState

    fun initScreen() {
        albumListUseCase.getAlbumList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ albumList ->
                _uiState.value =
                    MainUiState.Content(albumList.map {
                        AlbumUiItem(
                            username = it.username ?: "",
                            thumbnail = it.albumThumbnail,
                            albumTitle = it.albumTitle ?: "",
                            photoTitle = it.photoTitle
                        )
                    })
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
