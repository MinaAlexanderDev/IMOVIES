package com.example.imovies.navgation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.imovies.navgation.MyAppNavHost.Companion.DETAILS_SCREEN
import com.example.imovies.navgation.MyAppNavHost.Companion.HOME_SCREEN
import com.example.imovies.navgation.MyAppNavHost.Companion.MOVE_ID
import com.example.imovies.screens.home.HomeScreen
import com.example.imovies.screens.movie_details.movie.MovieDetailsScreen


@Preview
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),

    ) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HOME_SCREEN
    ) {

        composable(route = HOME_SCREEN) {
            HomeScreen(navController)
        }
        composable(
            route = DETAILS_SCREEN,
            arguments = listOf(navArgument(MOVE_ID) { type = NavType.IntType })
        )
        { navBackStackEntry ->
            navBackStackEntry.arguments?.getInt(MOVE_ID)?.let { it1 ->
                MovieDetailsScreen(
                    navController = navController,
                    movieId = it1
                )
            }
        }
    }
}


class MyAppNavHost {
    companion object {
        const val MOVE_ID = "moveId"
        private const val DETAILS = "details"
        const val HOME_SCREEN: String = "home"
        const val DETAILS_SCREEN: String = "$DETAILS/{$MOVE_ID}"

    }
}