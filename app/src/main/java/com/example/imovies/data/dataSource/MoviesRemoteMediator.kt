package com.example.imovies.data.dataSource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.imovies.data.local.MovieDatabase
import com.example.imovies.data.model.Movie
import com.example.imovies.data.remote.MoviesApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val moviesApi: MoviesApi,
    private val moviesDb: MovieDatabase,
) : RemoteMediator<Int, Movie>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, Movie>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.rowId / state.config.pageSize) + 1
                    }
                }
            }

            val movies = moviesApi.getPopularMovies(page = loadKey).results
            moviesDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDb.getMoviesDao().clearAllMovies()
                    moviesDb.getMoviesDao().clearPrimaryKey()
                }
                moviesDb.getMoviesDao().insertAll(movies)
            }
            MediatorResult.Success(
                endOfPaginationReached = movies.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}