package com.ptokaji.albumlist.data.di

import com.ptokaji.albumlist.data.datasource.UsersRepository
import com.ptokaji.albumlist.data.datasource.UsersRepositoryImpl
import com.ptokaji.albumlist.data.network.UsersApi
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
    abstract fun bindUsersRemoteDataSource(implementation: UsersRepositoryImpl): UsersRepository

    companion object {

        @Provides
        fun provideUsersApi(retrofit: Retrofit): UsersApi =
            retrofit.create(UsersApi::class.java)

        @Provides
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    }
}