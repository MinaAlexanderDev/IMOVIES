package com.example.imovies.data.repository

import com.example.imovies.data.model.MovieDetails
import com.example.imovies.util.Resource

interface IMovieDetailsRepository {

    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails>
}