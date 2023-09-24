package com.example.imovies.data.data_source

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.imovies.data.domain.MoviesPagerUseCase
import com.example.imovies.data.local.MovieDatabase
import com.example.imovies.data.remote.FakeApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MoviesRemoteMediatorTest {

    private var moviesApi = FakeApi()
    private lateinit var database: MovieDatabase
    private lateinit var useCase: MoviesPagerUseCase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        useCase = MoviesPagerUseCase(database, moviesApi)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `load_refresh_movies`() {
        runTest {
            // Arrange
            moviesApi.setGetPopularMovies(false)
            useCase.loadMoviePage().test {
                Assert.assertNotNull(awaitItem())
            }
        }
    }

    @Test
    fun `load_refresh_movies_with_empty_list`() {
        runTest {
            // Arrange
            moviesApi.setGetPopularMovies(true)
            useCase.loadMoviePage().test {
                Assert.assertNotNull(awaitItem())
            }
        }
    }

}


