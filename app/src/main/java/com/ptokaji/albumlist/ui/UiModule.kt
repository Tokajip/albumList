package com.ptokaji.albumlist.ui

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable

@Module
@InstallIn(ViewModelComponent::class)
class UiModule {

    companion object {
        @Provides
        fun provideCompositeDisposable() = CompositeDisposable()
    }
}