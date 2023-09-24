package com.example.imovies.commons.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imovies.data.model.Genre

@Composable
fun Genres(genresList: List<Genre>? = emptyList()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        LazyRow {
            genresList?.let { genres ->
                items(genres.size) { genre ->
                    Text(
                        text = genres[genre].name,
                        modifier = Modifier.padding(10.dp),
                        style = TextStyle(
                            fontSize = 24.sp,
                            color = Color.White, fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}