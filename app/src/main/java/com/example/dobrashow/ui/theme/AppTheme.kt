package com.example.dobrashow.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val darkColorTheme = AppColorsScheme(
    background = Color.White,
    onBackground = Color(0xFFD3CECE),
    primary = Color.Blue,
    onPrimary = Color(0xFF82B1FF),
    text = Color.White,
    onText = Color(0xFFB1A7A7),
)

private val lightColorTheme = AppColorsScheme(
    background = Color.Black,
    onBackground = Color(0xFF686868),
    primary = Color.Blue,
    onPrimary = Color(0xFF82B1FF),
    text = Color.White,
    onText = Color(0xFFB1A7A7),
)

private val typography = AppTypography(
    titleLarge = TextStyle(
        fontFamily = RedRose,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp
    ),
    titleNormal = TextStyle(
        fontFamily = RedRose,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = RedRose,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    bodyNormal = TextStyle(
        fontFamily = RedRose,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    bodySmall = TextStyle(
        fontFamily = RedRose,
        fontSize = 16.sp
    ),
    labelNormal = TextStyle(
        fontFamily = RedRose,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = RedRose,
        fontSize = 12.sp
    ),
)

private val shapes = AppShape(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(15.dp),
    large = RoundedCornerShape(50)
)

private val size = AppSize(
    large = 24.dp,
    medium = 16.dp,
    normal = 12.dp,
    small = 8.dp,
    extraSmall = 4.dp,
)

@Composable
fun AppTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = if (isDarkMode) darkColorTheme else lightColorTheme
    val rippleIndication = rememberRipple()
    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shapes,
        LocalAppSize provides size,
        LocalIndication provides rippleIndication,
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