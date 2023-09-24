package com.example.imovies.data.remote

import com.example.imovies.data.model.Genre
import com.example.imovies.data.model.Movie
import com.example.imovies.data.model.MovieDetails
import com.example.imovies.data.model.MoviesResponse
import okhttp3.Protocol
import okhttp3.Request
import retrofit2.Response

class FakeApi : MoviesApi {
    private val movies = listOf(
        Movie(id = 1, title = "Test Movie 1", rowId = 1),
        Movie(id = 2, title = "Test Movie 2", rowId = 2)
    )
    private var isNull: Boolean = false
    override suspend fun getPopularMovies(
        page: Int,
        pageSize: Int,
        apiKey: String,
        language: String
    ): MoviesResponse {

        return if (!isNull) {
            MoviesResponse(page = 1, totalPages = 10, totalResults = 100, results = movies)
        } else {
            MoviesResponse(page = 1, totalPages = 0, totalResults = 0, results = emptyList())
        }

    }

    override suspend fun getMovieDetails(
        movieId: Int,
        apiKey: String,
        language: String
    ): Response<MovieDetails> {

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
        return Response.success(
            movieDetails,
            okhttp3.Response.Builder()
                .code(200)
                .message("Response.success()")
                .protocol(Protocol.HTTP_1_1)
                .request(Request.Builder().url("http://test-url/").build())
                .receivedResponseAtMillis(1619053449513)
                .sentRequestAtMillis(1619053443814)
                .build()
        )
    }

    fun setGetPopularMovies(isNull: Boolean) {
        this.isNull = isNull
    }
}