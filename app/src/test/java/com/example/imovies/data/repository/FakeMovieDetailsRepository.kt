package com.example.imovies.data.repository

import com.example.imovies.data.model.Genre
import com.example.imovies.data.model.MovieDetails
import com.example.imovies.util.Resource

class FakeMovieDetailsRepository : IMovieDetailsRepository {

    private var isNull: Boolean = false
    private val movieDetails = MovieDetails(
        title = "Test Movie",
        releaseDate = "2021-01-01",
        runtime = 100,
        voteAverage = 8.0,
        genres = listOf(Genre(id = 1, name = "Action"), Genre(id = 2, name = "comedy")),
        overview = "Test movie overview"
    )

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails> {
        return if (!isNull) {
            println("getMoviesDetails445566")
            movieDetails.id = movieId
            Resource.Success(data = movieDetails)
        } else {

            Resource.Error(data = null)
        }
    }

    fun setNullable(isNull: Boolean) {
        this.isNull = isNull
    }
}