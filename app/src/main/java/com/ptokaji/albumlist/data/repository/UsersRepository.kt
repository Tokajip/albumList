package com.ptokaji.albumlist.data.repository

import com.ptokaji.albumlist.data.model.UserDataModel
import io.reactivex.rxjava3.core.Single

interface UsersRepository {
    fun getUsers(): Single<List<UserDataModel>>
}