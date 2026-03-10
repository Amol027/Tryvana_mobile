package com.example.myapplication.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.components.SearchBar
import com.example.myapplication.ui.screens.home.components.*
import com.example.myapplication.viewmodel.ProductViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import com.example.myapplication.ui.screens.home.components.DealsSection
import com.example.myapplication.ui.theme.BgColor
import androidx.compose.foundation.background



@Composable
fun HomeContent(
    rootNavController: NavHostController,
    productViewModel: ProductViewModel = viewModel()
) {

    val products = productViewModel.products
    val isLoading by productViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor),
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {

        // HEADER SECTION
        item {

            Column {

                HomeHeader()
                Spacer(modifier = Modifier.height(10.dp))


                Spacer(modifier = Modifier.height(12.dp))

                BannerSlider(
                    listOf(
                        com.example.myapplication.R.drawable.banner1,
                        com.example.myapplication.R.drawable.banner2,
                        com.example.myapplication.R.drawable.banner3,

                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                CategoryRow(
                    listOf(
                        Category("Fashion", "👗"),
                        Category("Mobiles", "📱"),
                        Category("Electronics", "🎧"),
                        Category("Home", "🏠"),
                        Category("Beauty", "💄"),
                        Category("Toys", "🧸")
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                OfferBanner()

                Spacer(modifier = Modifier.height(16.dp))

                DealsSection(
                    products = products.take(6),
                    rootNavController = rootNavController
                )


                Spacer(modifier = Modifier.height(20.dp))

            }
        }

        // LOADING STATE
        if (isLoading && products.isEmpty()) {

            item {
                ShimmerLoading()
            }

        } else {

            val chunkedProducts = products.chunked(2)

            items(chunkedProducts) { pair ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    pair.forEach { product ->

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                        ) {

                            ProductCard(
                                product = product,
                                rootNavController = rootNavController,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    if (pair.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
