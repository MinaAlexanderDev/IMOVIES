package com.example.imovies.commons.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.imovies.R
import com.example.imovies.data.model.Genre
import com.example.imovies.ui.theme.Transparent
import com.example.imovies.ui.theme.primaryDark

@Composable
fun MovieImageBanner(

    posterUrl: String,
    movieName: String,
    releaseDate: String,
    rating: Float,
    navigator: NavController,
    genres: List<Genre>? = emptyList(),
    overview: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Green, RoundedCornerShape(10.dp)),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 10.dp)
        ) {
            CircularBackButtons(onClick = {
                navigator.popBackStack()
            })
        }
        Box(
            modifier = Modifier.fillMaxSize(), Alignment.BottomStart
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = posterUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            placeholder(R.drawable.ic_placeholder)
                            crossfade(true)
                        }).build()
                ),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = "Movie Banner"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                Pair(0.3f, Transparent), Pair(1.5f, primaryDark)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = releaseDate, style = TextStyle(
                        fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold
                    ), modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                MovieInfo(
                    overview = overview.toString(),
                    releaseDate = releaseDate,
                )
                Genres(genres)
                Spacer(modifier = Modifier.height(5.dp))
                MovieNameAndRating(
                    movieName = movieName, rating = rating
                )
            }

        }
    }
}
