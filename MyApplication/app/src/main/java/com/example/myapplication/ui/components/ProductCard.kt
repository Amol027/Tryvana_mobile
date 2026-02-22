package com.example.myapplication.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.*

@Composable
fun ProductCard(
    title: String,
    price: String,
    imageUrl: String,   // 👈 emoji ki jagah imageUrl
    navController: NavController
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("detail/$title/$price/$imageUrl")
            },
        shape = RoundedCornerShape(12.dp)
    ) {

        Column {

            // 🔥 REAL IMAGE HERE
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),   // thoda height bada diya
                contentScale = ContentScale.Fit   // 👈 NO CROP
            )

            Column(modifier = Modifier.padding(10.dp)) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = price,
                    color = PrimaryTeal
                )

                Button(
                    onClick = {
                        navController.navigate("detail/$title/$price/$imageUrl")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentCoral
                    ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("View")
                }
            }
        }
    }
}
