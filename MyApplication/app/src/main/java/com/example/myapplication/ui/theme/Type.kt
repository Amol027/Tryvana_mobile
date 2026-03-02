package com.example.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

val Poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Poppins,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Poppins,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    bodyMedium = TextStyle(
        fontFamily = Poppins,
        fontSize = 14.sp
    )
)
