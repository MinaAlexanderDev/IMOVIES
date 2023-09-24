package com.example.imovies.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.imovies.MainCoroutineRule
import com.example.imovies.data.dataSource.MovieDetailsDataSourceImp
import com.example.imovies.data.model.Genre
import com.example.imovies.data.model.MovieDetails
import com.example.imovies.data.remote.FakeApi
import com.example.imovies.util.Resource
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@HiltAndroidTest
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val testCoroutineDispatcher = StandardTestDispatcher()
    private var api = FakeApi()
    private var movieDetailsDataSource = MovieDetailsDataSourceImp(api, testCoroutineDispatcher)
    private var repository = MovieDetailsRepository(movieDetailsDataSource)

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `getMoviesDetails returns success with movie details`() = runTest {

        // Arrange
        val movieId = 123
        val movieDetails = MovieDetails(
            id = movieId,
            title = "Test Movie",
            releaseDate = "2021-01-01",
            runtime = 100,
            voteAverage = 8.0,
            genres = listOf(Genre(id = 1, name = "Action"), Genre(id = 2, name = "comedy")),
            overview = "Test movie overview"
        )
        // Act
        api.setIsMovieDetails(false)
        val result = repository.getMovieDetails(movieId)

        // Assert
        assertEquals(Resource.Success(movieDetails).data?.id, result.data?.id)
    }

    @Test
    fun `getMoviesDetails returns error with message when api return null`() = runTest {
        // Arrange
        val movieId = 123

        // Act
        api.setIsMovieDetails(true)
        val result = repository.getMovieDetails(movieId)

        // Assert
        assertEquals(Resource.Success(null).data, result.data)

    }
}
