package com.example.quizapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.quizapp.R

// ---------------------------------------------------------
// 1) Definir la familia tipográfica completa (Lexend)
// ---------------------------------------------------------
val Lexend = FontFamily(
    Font(R.font.lexend_extrabold, FontWeight.ExtraBold),
    Font(R.font.lexend_bold, FontWeight.Bold),
    Font(R.font.lexend_medium, FontWeight.Medium),
    Font(R.font.lexend_regular, FontWeight.Normal)
)

// ---------------------------------------------------------
// 2) Material Typography con tu fuente aplicada correctamente
// ---------------------------------------------------------

val Typography = Typography(

    titleLarge = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),

    labelLarge = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),

    labelSmall = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.3.sp
    )
)
