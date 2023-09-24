package com.example.imovies.data.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.imovies.data.dataSource.MoviesRemoteMediator
import com.example.imovies.data.local.MovieDatabase
import com.example.imovies.data.model.Movie
import com.example.imovies.data.remote.MoviesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesPagerUseCase @Inject constructor(
    private val movieDb: MovieDatabase,
    private val api: MoviesApi
) : IMoviesPagerUseCase {

    @OptIn(ExperimentalPagingApi::class)
    override fun loadMoviePage(): Flow<PagingData<Movie>> {
        return Pager(config = PagingConfig(pageSize = 10), remoteMediator = MoviesRemoteMediator(
            moviesDb = movieDb, moviesApi = api
        ), pagingSourceFactory = {
            movieDb.getMoviesDao().moviesPagingSource()
        }).flow
    }
}