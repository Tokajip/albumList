package com.ptokaji.albumlist.domain.usecase

import com.ptokaji.albumlist.domain.model.AlbumDomainModel
import io.reactivex.rxjava3.core.Single

interface AlbumListUseCase {

    fun getAlbumList(): Single<List<AlbumDomainModel>>
}