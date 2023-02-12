package com.ptokaji.albumlist.domain.di

import com.ptokaji.albumlist.domain.usecase.AlbumListUseCase
import com.ptokaji.albumlist.domain.usecase.AlbumListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindAlbumListUseCase(impl: AlbumListUseCaseImpl): AlbumListUseCase
}