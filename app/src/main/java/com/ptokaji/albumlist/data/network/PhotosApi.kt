package com.ptokaji.albumlist.data.network

import com.ptokaji.albumlist.data.model.PhotoDataModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface PhotosApi {

    @GET("/photos")
    fun getPhotos(): Single<List<PhotoDataModel>>
}