package com.example.imovies.data.dataSource

import com.example.imovies.data.remote.MoviesApi
import com.example.imovies.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MovieDetailsDataSourceImp @Inject constructor(
    private val api: MoviesApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : IMovieDetailsDataSource {
    override suspend fun getMoviesDetailsDataSource(movieId: Int) =
        withContext(ioDispatcher) {
            return@withContext api.getMovieDetails(movieId)

        }

}

