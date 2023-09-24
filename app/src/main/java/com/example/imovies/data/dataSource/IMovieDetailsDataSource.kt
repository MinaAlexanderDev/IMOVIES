package com.example.imovies.data.dataSource

import com.example.imovies.data.model.MovieDetails
import retrofit2.Response
import javax.inject.Singleton

@Singleton
interface IMovieDetailsDataSource {
    suspend fun getMoviesDetailsDataSource(movieId: Int): Response<MovieDetails>
}