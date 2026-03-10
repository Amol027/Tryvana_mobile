package com.example.myapplication.ui.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.data.model.Product
import androidx.compose.ui.platform.LocalConfiguration


@Composable
fun DealsSection(
    products: List<Product>,
    rootNavController: NavHostController
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = (screenWidth - 48.dp) / 2   // same width as grid cards

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "🔥 Deals of the Day",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(products) { product ->

                ProductCard(
                    product = product,
                    rootNavController = rootNavController,
                    modifier = Modifier.width(cardWidth)
                )

            }

        }

    }
}



