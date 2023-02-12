package com.ptokaji.albumlist.data.repository

import com.ptokaji.albumlist.data.model.AlbumDataModel
import io.reactivex.rxjava3.core.Single

interface AlbumsRepository {
    fun getAlbums(): Single<List<AlbumDataModel>>
}