package com.ptokaji.albumlist.domain.usecase

import com.ptokaji.albumlist.data.repository.AlbumsRepository
import com.ptokaji.albumlist.data.repository.PhotosRepository
import com.ptokaji.albumlist.data.repository.UsersRepository
import com.ptokaji.albumlist.domain.model.AlbumDomainModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AlbumListUseCaseImplTest {

    @MockK(relaxed = true)
    lateinit var usersRepository: UsersRepository

    @MockK(relaxed = true)
    lateinit var albumsRepository: AlbumsRepository

    @MockK(relaxed = true)
    lateinit var photosRepository: PhotosRepository

    @InjectMockKs
    lateinit var sut: AlbumListUseCaseImpl

    lateinit var testSubscriber: TestObserver<List<AlbumDomainModel>>

    private val mockError = java.lang.NullPointerException()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        testSubscriber = TestObserver<List<AlbumDomainModel>>()
    }

    @Test
    fun `GIVEN getUsers API call returns error WHEN getAlbumList THEN return error`() {
        // GIVEN
        every { usersRepository.getUsers() } returns Single.error(mockError)

        // WHEN
        sut.getAlbumList().subscribe(testSubscriber)

        // THEN
        testSubscriber.assertError(mockError)
    }

    @Test
    fun `GIVEN getPhotos API call returns error WHEN getAlbumList THEN return error`() {
        // GIVEN
        every { photosRepository.getPhotos() } returns Single.error(mockError)

        // WHEN
        sut.getAlbumList().subscribe(testSubscriber)

        // THEN
        testSubscriber.assertError(mockError)
    }

    @Test
    fun `GIVEN getAlbums API call returns error WHEN getAlbumList THEN return error`() {
        // GIVEN
        every { albumsRepository.getAlbums() } returns Single.error(mockError)

        // WHEN
        sut.getAlbumList().subscribe(testSubscriber)

        // THEN
        testSubscriber.assertError(mockError)
    }

    @Test
    fun `GIVEN getPhotos returns multiple item with same albumId WHEN getAlbumList THEN return one per albumId`() {
        // GIVEN
        every { albumsRepository.getAlbums() } returns Single.just(emptyList())
        every { usersRepository.getUsers() } returns Single.just(emptyList())
        every { photosRepository.getPhotos() } returns Single.just(listOf(
            mockk(relaxed = true) {
                every { albumId } returns 1
                every { id } returns 1
            },
            mockk(relaxed = true) {
                every { albumId } returns 1
                every { id } returns 2
            }
        ))

        // WHEN
        sut.getAlbumList().subscribe(testSubscriber)

        // THEN
        testSubscriber.assertNoErrors()
        assertEquals(1, testSubscriber.values().first().size)
    }

    @Test
    fun `GIVEN albums list not contain photo albumId WHEN getAlbumList THEN return error`() {
        // GIVEN
        every { albumsRepository.getAlbums() } returns Single.just(emptyList())
        every { usersRepository.getUsers() } returns Single.just(emptyList())
        every { photosRepository.getPhotos() } returns Single.just(listOf(mockk {
            every { id } returns 1
            every { albumId } returns 2
            every { title } returns "mockTitle"
            every { thumbnailUrl } returns "mockThumbnail"
        }))

        // WHEN
        sut.getAlbumList().subscribe(testSubscriber)

        // THEN
        testSubscriber.assertNoErrors()
        assertEquals("mockThumbnail", testSubscriber.values().first().first().albumThumbnail)
        assertEquals("mockTitle", testSubscriber.values().first().first().photoTitle)
        assertEquals(null, testSubscriber.values().first().first().albumTitle)
        assertEquals(null, testSubscriber.values().first().first().username)
    }

    @Test
    fun `GIVEN albums list contains photo albumId WHEN getAlbumList THEN return album title`() {
        // GIVEN
        every { albumsRepository.getAlbums() } returns Single.just(listOf(mockk {
            every { id } returns 2
            every { userId } returns 1
            every { title } returns "mockAlbumTitle"
        }))
        every { usersRepository.getUsers() } returns Single.just(emptyList())
        every { photosRepository.getPhotos() } returns Single.just(listOf(mockk {
            every { id } returns 1
            every { albumId } returns 2
            every { title } returns "mockTitle"
            every { thumbnailUrl } returns "mockThumbnail"
        }))

        // WHEN
        sut.getAlbumList().subscribe(testSubscriber)

        // THEN
        testSubscriber.assertNoErrors()
        assertEquals("mockThumbnail", testSubscriber.values().first().first().albumThumbnail)
        assertEquals("mockTitle", testSubscriber.values().first().first().photoTitle)
        assertEquals("mockAlbumTitle", testSubscriber.values().first().first().albumTitle)
        assertEquals(null, testSubscriber.values().first().first().username)
    }

    @Test
    fun `GIVEN users list contains album userId WHEN getAlbumList THEN return album title`() {
        // GIVEN
        every { usersRepository.getUsers() } returns Single.just(listOf(mockk {
            every { id } returns 1
            every { name } returns "mockName"
            every { username } returns "mockUsername"
        }))
        every { albumsRepository.getAlbums() } returns Single.just(listOf(mockk {
            every { id } returns 2
            every { userId } returns 1
            every { title } returns "mockAlbumTitle"
        }))
        every { photosRepository.getPhotos() } returns Single.just(listOf(mockk {
            every { id } returns 1
            every { albumId } returns 2
            every { title } returns "mockTitle"
            every { thumbnailUrl } returns "mockThumbnail"
        }))

        // WHEN
        sut.getAlbumList().subscribe(testSubscriber)

        // THEN
        testSubscriber.assertNoErrors()
        assertEquals("mockThumbnail", testSubscriber.values().first().first().albumThumbnail)
        assertEquals("mockTitle", testSubscriber.values().first().first().photoTitle)
        assertEquals("mockAlbumTitle", testSubscriber.values().first().first().albumTitle)
        assertEquals("mockUsername", testSubscriber.values().first().first().username)
    }
}