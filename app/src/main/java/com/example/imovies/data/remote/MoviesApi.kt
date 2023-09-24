package com.example.imovies.data.remote


import com.example.imovies.data.model.MovieDetails
import com.example.imovies.data.model.MoviesResponse
import com.example.imovies.util.Constants.API_KEY
import com.example.imovies.util.Constants.LANGUAGE
import com.example.imovies.util.Constants.PAGE_SIZE
import com.example.imovies.util.Constants.STARTING_PAGE_INDEX
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET(POPULAR_MOVIE)
    suspend fun getPopularMovies(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("page_size") pageSize: Int = PAGE_SIZE,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): MoviesResponse

    @GET(MOVIE)
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): Response<MovieDetails>

    companion object {
        const val POPULAR_MOVIE = "movie/popular"
        const val MOVIE = "movie/{movie_id}"
    }
}