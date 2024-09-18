package com.example.uikit

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val darkColorTheme = AppColorsScheme(
    background = Color(0xFF000000),
    onBackground = Color(0xFFD3CECE),
    primary = Color(0xFFFFFF4C),
    onPrimary = Color(0xFFFFFF8D),
    text = Color(0xFFE7DEDE),
    onText = Color(0xFFB1A7A7),
    transparent = Color(0x00000000),
    error = Color(0xFFE21010)
)

private val lightColorTheme = AppColorsScheme(
    background = Color(0xFF000000),
    onBackground = Color(0xFF686868),
    primary = Color(0xFFFFFF4C),
    onPrimary = Color(0xFFFFFF8D),
    text = Color(0xFFE7DEDE),
    onText = Color(0xFFB1A7A7),
    transparent = Color(0x00000000),
    error = Color(0xFFE21010)
)

private val typography = AppTypography(
    titleLarge = TextStyle(
        fontFamily = RedRose,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    titleNormal = TextStyle(
        fontFamily = RedRose,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    titleSmall = TextStyle(
        fontFamily = RedRose,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = RedRose,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    bodyNormal = TextStyle(
        fontFamily = RedRose,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = RedRose,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = RedRose,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    labelNormal = TextStyle(
        fontFamily = RedRose,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = RedRose,
        fontSize = 14.sp
    ),
)

private val shapes = AppShape(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(15.dp),
    large = RoundedCornerShape(50)
)

private val size = AppSize(
    dp24 = 24.dp,
    dp16 = 16.dp,
    dp12 = 12.dp,
    dp10 = 10.dp,
    dp8 = 8.dp,
    dp4 = 4.dp,
    dp2 = 2.dp,
    dp1 = 1.dp
)

@Composable
fun AppTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = if (isDarkMode) darkColorTheme else lightColorTheme
    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shapes,
        LocalAppSize provides size,
        content = content
    )

}

object AppTheme {
    val colorScheme: AppColorsScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current

    val shape: AppShape
        @Composable get() = LocalAppShape.current

    val size: AppSize
        @Composable get() = LocalAppSize.current

}