package com.example.imovies.data.domain

import androidx.paging.PagingData
import com.example.imovies.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class FakeMoviesPagerUseCaseTest : IMoviesPagerUseCase {
    private val movies = listOf(
        Movie(id = 1, title = "Movie 1", rowId = 1), Movie(id = 2, title = "Movie 2", rowId = 2)
    )

    private val pagingData = PagingData.from(movies)
    private val pagingDataNull: PagingData<Movie> = PagingData.from(emptyList())
    private var isNull: Boolean = false

    override fun loadMoviePage(): Flow<PagingData<Movie>> {
        return if (!isNull) {
            { pagingData }.asFlow()
        } else {
            { pagingDataNull }.asFlow()
        }
    }

    fun setNullable(isNull: Boolean) {
        this.isNull = isNull
    }
}
