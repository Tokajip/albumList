package com.ptokaji.albumlist.data.datasource

import com.ptokaji.albumlist.data.model.UserDataModel
import io.reactivex.rxjava3.core.Single

interface UsersRepository {
    fun getUsers(): Single<List<UserDataModel>>
}