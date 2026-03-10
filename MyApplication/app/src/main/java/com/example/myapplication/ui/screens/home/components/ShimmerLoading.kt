package com.example.myapplication.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerLoading() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        repeat(3) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                repeat(2) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .background(
                                    Color.LightGray,
                                    RoundedCornerShape(8.dp)
                                )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Box(
                            modifier = Modifier
                                .height(14.dp)
                                .fillMaxWidth()
                                .background(
                                    Color.LightGray,
                                    RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
            }
        }
    }
}
