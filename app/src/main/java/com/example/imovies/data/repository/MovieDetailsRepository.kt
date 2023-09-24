package com.example.imovies.data.repository

import com.example.imovies.data.dataSource.IMovieDetailsDataSource
import com.example.imovies.data.model.MovieDetails
import com.example.imovies.util.Resource
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    private val movieDetailsDataSource: IMovieDetailsDataSource
) : IMovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails> {
        try {
            val response = movieDetailsDataSource.getMoviesDetailsDataSource(movieId)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    return Resource.Success(body)
                }
            } else {
                return Resource.Error()
            }
            return Resource.Error()
        } catch (e: Exception) {
            return Resource.Error(message = e.message)
        }
    }
}