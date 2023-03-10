package com.ptokaji.albumlist.data.network

import com.ptokaji.albumlist.data.model.UserDataModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface UsersApi {

    @GET("/users")
    fun getUsers(): Single<List<UserDataModel>>
}