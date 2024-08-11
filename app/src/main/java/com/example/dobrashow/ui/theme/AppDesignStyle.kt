package com.example.dobrashow.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class AppColorsScheme(
    val background: Color,          //цвет фона
    val onBackground: Color,        //оттенок фона
    val primary: Color,             //основной цвет
    val onPrimary: Color,           //оттенок основного цвета
    val text: Color,                //цвет текста
    val onText: Color,              //оттенок текста
)

data class AppTypography(
    val titleLarge: TextStyle,       //шрифт для заголовков
    val titleNormal: TextStyle,
    val bodyLarge: TextStyle,        //шрифт для больших текстов
    val bodyNormal: TextStyle,
    val bodySmall: TextStyle,
    val labelNormal: TextStyle,       //шрифт для текста внутри компонентов
    val labelSmall: TextStyle,
)

data class AppShape(
    val small: Shape,
    val medium: Shape,
    val large: Shape,
)

data class AppSize(
    val large: Dp,
    val medium: Dp,
    val normal: Dp,
    val small: Dp,
    val extraSmall: Dp,
)

val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorsScheme(
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        text = Color.Unspecified,
        onText = Color.Unspecified,
    )
}

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        titleLarge = TextStyle.Default,
        titleNormal = TextStyle.Default,
        bodyLarge = TextStyle.Default,
        bodyNormal = TextStyle.Default,
        bodySmall = TextStyle.Default,
        labelNormal = TextStyle.Default,
        labelSmall = TextStyle.Default,
    )
}

val LocalAppShape = staticCompositionLocalOf {
    AppShape(
        small = RectangleShape,
        medium = RectangleShape,
        large = RectangleShape
    )
}

val LocalAppSize = staticCompositionLocalOf {
    AppSize(
        large = Dp.Unspecified,
        medium = Dp.Unspecified,
        normal = Dp.Unspecified,
        small = Dp.Unspecified,
        extraSmall = Dp.Unspecified
    )
}