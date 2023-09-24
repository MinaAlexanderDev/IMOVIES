package com.example.imovies.data.local


import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.imovies.data.model.MockPagingSource
import com.example.imovies.data.model.Movie
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MovieDatabaseTest {
    private lateinit var database: MovieDatabase
    private val moviesList = listOf(
        Movie(id = 1, title = "Test Movie 1", rowId = 1),
        Movie(id = 2, title = "Test Movie 2", rowId = 2)
    )

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), MovieDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `insert_movies_and_rest_keys_get_first_movie_row_id_one`() = runTest {
        // Arrange
        val mockPagingSource = MockPagingSource(moviesList)
        val moviesPagingSource = mockPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = true
            )
        ) as? PagingSource.LoadResult.Page
        val movieDao = database.getMoviesDao()
        movieDao.insertAll(moviesList)
        movieDao.clearAllMovies()
        movieDao.clearPrimaryKey()
        movieDao.insertAll(moviesList)
        // Act
        val result = movieDao.moviesPagingSource().load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = true
            )
        ) as? PagingSource.LoadResult.Page

        assertThat((moviesPagingSource)?.data?.get(0)?.rowId).isEqualTo(
            (result)?.data?.get(0)?.rowId
        )
    }

    @Test
    fun `insert_delete_and_get_empty_movie`() = runTest {
        // Arrange
        val movieDao = database.getMoviesDao()
        movieDao.insertAll(moviesList)
        movieDao.clearAllMovies()

        // Act
        val result = movieDao.moviesPagingSource().load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = true
            )
        ) as? PagingSource.LoadResult.Page

        assertThat((result)?.data).isEqualTo(
            emptyList()
        )
    }

    @Test
    fun `insert_empty_and_get_empty_movies`() = runTest {
        // Arrange
        val mockPagingSource = MockPagingSource(emptyList())
        val moviesPagingSource = mockPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = true
            )
        ) as? PagingSource.LoadResult.Page

        val movieDao = database.getMoviesDao()
        movieDao.insertAll(emptyList())

        // Act
        val result = movieDao.moviesPagingSource().load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = true
            )
        ) as? PagingSource.LoadResult.Page


        assertThat((moviesPagingSource)?.data).isEqualTo(
            (result)?.data
        )
    }

    @Test
    fun `insert_and_get_movies`() = runTest {
        // Arrange
        val mockPagingSource = MockPagingSource(moviesList)
        val moviesPagingSource = mockPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = true
            )
        ) as? PagingSource.LoadResult.Page

        val movieDao = database.getMoviesDao()
        movieDao.insertAll(moviesList)

        // Act

        val result = movieDao.moviesPagingSource().load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = true
            )
        ) as? PagingSource.LoadResult.Page

        assertThat((moviesPagingSource)?.data).isEqualTo(
            (result)?.data
        )

    }
}


