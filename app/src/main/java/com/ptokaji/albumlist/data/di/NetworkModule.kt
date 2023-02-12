package com.ptokaji.albumlist.data.di

import com.ptokaji.albumlist.data.network.AlbumsApi
import com.ptokaji.albumlist.data.network.PhotosApi
import com.ptokaji.albumlist.data.network.UsersApi
import com.ptokaji.albumlist.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindUsersRemoteRepository(implementation: UsersRepositoryImpl): UsersRepository

    @Binds
    abstract fun bindPhotosRemoteRepository(implementation: PhotosRepositoryImpl): PhotosRepository

    @Binds
    abstract fun bindAlbumsRemoteRepository(implementation: AlbumsRepositoryImpl): AlbumsRepository

    companion object {

        @Provides
        fun provideUsersApi(retrofit: Retrofit): UsersApi =
            retrofit.create(UsersApi::class.java)

        @Provides
        fun providePhotosApi(retrofit: Retrofit): PhotosApi =
            retrofit.create(PhotosApi::class.java)

        @Provides
        fun provideAlbumsApi(retrofit: Retrofit): AlbumsApi =
            retrofit.create(AlbumsApi::class.java)

        @Provides
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    }
}