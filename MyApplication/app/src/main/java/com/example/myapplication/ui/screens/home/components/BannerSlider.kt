package com.example.myapplication.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.foundation.ExperimentalFoundationApi

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerSlider(
    banners: List<Int>
) {

    val pagerState = rememberPagerState(pageCount = { banners.size })

    // Auto Slide
    LaunchedEffect(Unit) {

        while (true) {

            delay(3000)

            val nextPage = (pagerState.currentPage + 1) % banners.size

            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 12.dp
        ) { page ->

            Card(
                modifier = Modifier.fillMaxSize()
            ) {

                Image(
                    painter = painterResource(id = banners[page]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Indicator Dots
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            repeat(banners.size) { index ->

                val color =
                    if (pagerState.currentPage == index) Color.Black
                    else Color.LightGray

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .background(color, CircleShape)
                )
            }
        }
    }
}
