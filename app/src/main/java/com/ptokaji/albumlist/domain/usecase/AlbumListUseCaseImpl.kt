package com.ptokaji.albumlist.domain.usecase

import com.ptokaji.albumlist.data.repository.AlbumsRepository
import com.ptokaji.albumlist.data.repository.PhotosRepository
import com.ptokaji.albumlist.data.repository.UsersRepository
import com.ptokaji.albumlist.domain.model.AlbumDomainModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AlbumListUseCaseImpl @Inject constructor(
    private val usersRepository: UsersRepository,
    private val photosRepository: PhotosRepository,
    private val albumsRepository: AlbumsRepository
) : AlbumListUseCase {
    override fun getAlbumList(): Single<List<AlbumDomainModel>> {
        return Single.zip(
            usersRepository.getUsers(),
            photosRepository.getPhotos().map { photoList -> photoList.distinctBy { it.albumId } },
            albumsRepository.getAlbums()
        ) { users, photos, albums ->
            photos.map { photo ->
                AlbumDomainModel(
                    albumThumbnail = photo.thumbnailUrl,
                    photoTitle = photo.title,
                    albumTitle = albums.firstOrNull { album -> album.id == photo.albumId }?.title
                        ?: "",
                    users.firstOrNull { user -> user.id == albums.firstOrNull { album -> album.id == photo.albumId }?.userId }?.username
                        ?: "",
                )
            }
        }
    }
}