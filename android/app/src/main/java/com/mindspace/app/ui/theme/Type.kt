package com.mindspace.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val MindSpaceFontFamily = FontFamily.SansSerif

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = MindSpaceFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 34.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.5).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = MindSpaceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = MindSpaceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    titleLarge = TextStyle(
        fontFamily = MindSpaceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = MindSpaceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MindSpaceFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.2.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = MindSpaceFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.15.sp
    ),
    bodySmall = TextStyle(
        fontFamily = MindSpaceFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MindSpaceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = MindSpaceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.6.sp
    ),
    labelSmall = TextStyle(
        fontFamily = MindSpaceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.7.sp
    )
)
