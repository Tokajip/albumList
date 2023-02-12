package com.ptokaji.albumlist.data.repository

import com.ptokaji.albumlist.data.model.UserDataModel
import com.ptokaji.albumlist.data.network.UsersApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(private val usersApi: UsersApi) : UsersRepository {
    override fun getUsers(): Single<List<UserDataModel>> {
        return usersApi.getUsers()
    }
}