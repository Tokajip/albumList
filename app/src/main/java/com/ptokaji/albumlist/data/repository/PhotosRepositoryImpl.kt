package com.ptokaji.albumlist.data.repository

import com.ptokaji.albumlist.data.model.PhotoDataModel
import com.ptokaji.albumlist.data.network.PhotosApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(private val photosApi: PhotosApi) : PhotosRepository {
    override fun getPhotos(): Single<List<PhotoDataModel>> {
        return photosApi.getPhotos()
    }
}