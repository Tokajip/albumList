package com.ptokaji.albumlist.data.network

import com.ptokaji.albumlist.data.model.UserDataModel
import retrofit2.Response
import retrofit2.http.GET

interface UsersApi {

    @GET("/users")
    suspend fun getUsers(): Response<List<UserDataModel>>
}