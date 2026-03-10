package com.example.myapplication.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.data.model.Product
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.*

@Composable
fun ProductCard(
    product: Product,
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier

            .height(260.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {

        Column {

            // Product Image
            Box {

                AsyncImage(
                    model = product.image_url,
                    contentDescription = product.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),  // ✅ fixed image height
                    contentScale = ContentScale.Crop
                )

                // ❤️ Wishlist
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist"
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        text = product.title,
                        maxLines = 2,  // ✅ fixed lines
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "⭐ 4.3 • 1200 ratings"
                    )
                }

                Column {

                    Text(
                        text = "₹${product.price}",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Free Delivery"
                    )
                }
            }
        }
    }
}
