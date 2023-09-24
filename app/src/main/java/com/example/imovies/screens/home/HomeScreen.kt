package com.example.imovies.screens.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.imovies.commons.ui.MovieList

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val popularMovies = viewModel.moviesList.collectAsLazyPagingItems()
    MovieList(movies = popularMovies, navController = navController)
}

