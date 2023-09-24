package com.example.imovies.screens.movie_details.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.imovies.MainCoroutineRule
import com.example.imovies.data.model.Genre
import com.example.imovies.data.model.MovieDetails
import com.example.imovies.data.repository.FakeMovieDetailsRepository
import com.example.imovies.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher)
    private var repository = FakeMovieDetailsRepository()
    private var viewModel = MovieDetailsViewModel(repository, testCoroutineDispatcher)


    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMovieDetails returns success with movie details`() = testCoroutineScope.runTest {
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
        val success = Resource.Success(movieDetails)
        repository.setNullable(false)
        // Act
        viewModel.getMovieDetails(movieId)
        delay(200)
        //Assert
        viewModel.movieDetails.test {
            val item = awaitItem()
            assertEquals(success.data?.id, item.data?.id)
        }

    }

    @Test
    fun `getMovieDetails returns error with exception`() = testCoroutineScope.runTest {
        // Arrange
        val movieId = 123
        val error = Resource.Error(null)
        repository.setNullable(true)

        // Act
        viewModel.getMovieDetails(movieId)
        delay(200)
        //Assert
        viewModel.movieDetails.test {
            assertEquals(error.data, awaitItem().data)
        }
    }

}



