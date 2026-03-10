package com.example.myapplication.ui.screens.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable

@Composable
fun WishlistIcon(modifier: Modifier = Modifier) {

    var liked by remember { mutableStateOf(false) }

    Icon(
        imageVector = if (liked)
            Icons.Filled.Favorite
        else
            Icons.Outlined.FavoriteBorder,

        contentDescription = "wishlist",
        tint = if (liked) Color.Red else Color.White,
        modifier = modifier.clickable {
            liked = !liked
        }
    )
}
