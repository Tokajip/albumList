package com.ptokaji.albumlist.data.repository

import com.ptokaji.albumlist.data.model.PhotoDataModel
import io.reactivex.rxjava3.core.Single

interface PhotosRepository {
    fun getPhotos(): Single<List<PhotoDataModel>>
}