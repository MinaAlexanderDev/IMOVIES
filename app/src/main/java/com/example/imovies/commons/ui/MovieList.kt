package com.example.imovies.commons.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.imovies.data.model.Movie
import com.example.imovies.navgation.MyAppNavHost
import com.example.imovies.navgation.MyAppNavHost.Companion.MOVE_ID
import com.example.imovies.util.Constants

@Composable
fun MovieList(
    movies: LazyPagingItems<Movie>,
    navController: NavController
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = movies.loadState) {
        if (movies.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (movies.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if (movies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item { Spacer(modifier = Modifier.padding(4.dp)) }

                items(movies) { movie ->
                    if (movie != null) {
                        MovieItem(
                            imageUrl = "${Constants.IMAGE_BASE_UR}/${movie.posterPath!!}",
                            title = movie.title,
                            date = movie.releaseDate!!.toString(),
                            onClick = {
                                navController.navigate(
                                    MyAppNavHost.DETAILS_SCREEN.replace(
                                        oldValue = "{$MOVE_ID}",
                                        newValue = movie.id.toString()
                                    )
                                )
                            }
                        )
                    }
                }
                item {
                    if (movies.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}