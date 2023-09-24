package com.example.imovies.commons.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.imovies.R

@Composable
fun MovieItem(imageUrl: String, title: String?, date: String?, onClick: () -> Unit) {
    Card {
        Column {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            placeholder(R.drawable.ic_placeholder)
                            crossfade(true)
                        }).build()
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { onClick.invoke() },
                contentScale = ContentScale.Crop,
                contentDescription = "Movie Banner"
            )
            Spacer(modifier = Modifier.height(10.dp))
            title?.let { Text(text = it) }
            Spacer(modifier = Modifier.height(10.dp))
            date?.let { Text(text = it) }
        }

    }
}

@Preview
@Composable
fun MovieItemPreview() {
    MovieItem(onClick = { }, imageUrl = "", title = "Title", date = "22/9/2023")
}