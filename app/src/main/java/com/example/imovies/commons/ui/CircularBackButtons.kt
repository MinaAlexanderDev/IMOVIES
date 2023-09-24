package com.example.imovies.commons.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.imovies.R
import com.example.imovies.ui.theme.primaryPink

@Composable
fun CircularBackButtons(
    color: Color = Color.Gray,
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    onClick: () -> Unit = {}
) {
    Button(
        onClick = { onClick() },
        contentPadding = PaddingValues(),
        shape = CircleShape,
        elevation = elevation,
        modifier = Modifier
            .width(38.dp)
            .height(38.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White.copy(alpha = 0.3f),
            contentColor = color
        )
    ) {
        IconButton(onClick = {
            onClick()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_left),
                tint = primaryPink,
                contentDescription = null
            )
        }
    }
}