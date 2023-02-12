package com.ptokaji.albumlist.data.network

import com.ptokaji.albumlist.data.model.AlbumDataModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface AlbumsApi {

    @GET("/albums")
    fun getAlbums(): Single<List<AlbumDataModel>>
}