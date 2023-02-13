package com.ptokaji.albumlist.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ptokaji.albumlist.domain.model.AlbumDomainModel
import com.ptokaji.albumlist.domain.usecase.AlbumListUseCase
import com.ptokaji.albumlist.ui.main.model.MainUiState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var getAlbumListUseCase: AlbumListUseCase

    @RelaxedMockK
    lateinit var compositeDisposable: CompositeDisposable

    @InjectMockKs
    lateinit var sut: MainViewModel

    @RelaxedMockK
    lateinit var stateObserver: Observer<MainUiState>

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        sut.uiState.observeForever(stateObserver)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `WHEN sut created THEN uiState is LOADING`() {
        assertEquals(MainUiState.Loading, sut.uiState.value)
    }

    @Test
    fun `GIVEN getAlbumList has error WHEN initScreen called THEN uiState is ERROR`() {
        // GIVEN
        val mockError = java.lang.NullPointerException("mockException")
        every { getAlbumListUseCase.getAlbumList() } returns Single.error(mockError)
        // WHEN
        sut.initScreen()
        // THEN
        verify { getAlbumListUseCase.getAlbumList() }
        verify { compositeDisposable.add(any()) }
        verify { stateObserver.onChanged(MainUiState.Error("mockException")) }
    }

    @Test
    fun `GIVEN getAlbumList return success WHEN initScreen called THEN uiState is CONTENT`() {
        // GIVEN
        val mockAlbum = mockk<AlbumDomainModel>(relaxed = true) {
            every { photoTitle } returns "photoTitle"
            every { albumThumbnail } returns "albumThumbnail"
            every { albumTitle } returns "albumTitle"
            every { username } returns "username"
        }
        val state = mutableListOf<MainUiState>()
        every { getAlbumListUseCase.getAlbumList() } returns Single.just(listOf(mockAlbum))
        // WHEN
        sut.initScreen()
        // THEN
        verify { getAlbumListUseCase.getAlbumList() }
        verify { compositeDisposable.add(any()) }
        verify { stateObserver.onChanged(capture(state)) }

        val outputState = state.last() as MainUiState.Content
        assertEquals(outputState.uiList.size, 1)
        assertEquals(outputState.uiList.first().albumTitle, "albumTitle")
        assertEquals(outputState.uiList.first().photoTitle, "photoTitle")
        assertEquals(outputState.uiList.first().username, "username")
        assertEquals(outputState.uiList.first().thumbnail, "albumThumbnail")
    }
}