package com.ptokaji.albumlist.data.repository

import com.ptokaji.albumlist.data.model.AlbumDataModel
import com.ptokaji.albumlist.data.network.AlbumsApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AlbumsRepositoryImpl @Inject constructor(
    private val albumsApi: AlbumsApi
) : AlbumsRepository {
    override fun getAlbums(): Single<List<AlbumDataModel>> {
        return albumsApi.getAlbums()
    }
}