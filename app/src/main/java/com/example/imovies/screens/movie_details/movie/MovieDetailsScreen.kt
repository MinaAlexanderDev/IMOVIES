package com.example.imovies.screens.movie_details.movie

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.imovies.commons.ui.MovieImageBanner
import com.example.imovies.util.Constants
import com.example.imovies.util.Resource

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    navController: NavHostController,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = movieId) { viewModel.getMovieDetails(movieId) }
    val details = viewModel.movieDetails.collectAsState().value
    val context = LocalContext.current
    Column {
        when (details) {
            is Resource.Success -> {

                MovieImageBanner(
                    posterUrl = "${Constants.IMAGE_BASE_UR}/${details.data?.posterPath}",
                    movieName = details.data?.title.toString(),
                    releaseDate = details.data?.releaseDate.toString(),
                    rating = details.data?.voteAverage?.toFloat()!!,
                    navigator = navController,
                    genres = details.data.genres,
                    overview = details.data.overview
                )
            }

            is Resource.Error -> {
                Toast.makeText(context, "message", Toast.LENGTH_SHORT).show()
            }

            is Resource.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}
