package com.example.myapplication.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

// 🔥 Trywana Colors
val PrimaryTeal = Color(0xFF008080)
val AccentCoral = Color(0xFFFF6F61)
val BgColor = Color(0xFFE8FFF3)
val TextDark = Color(0xFF333333)
val TextLight = Color(0xFF888888)

@Composable
fun CategoryRow(
    categories: List<Category>
) {

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        items(categories) { category ->

            Column(
                modifier = Modifier.width(72.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(
                    shape = RoundedCornerShape(16.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                BgColor,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = category.icon
                        )

                    }

                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = category.name,
                    color = TextDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )

            }

        }

    }
}
