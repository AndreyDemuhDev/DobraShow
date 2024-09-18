package com.example.uikit

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
    val transparent: Color,         //прозрачный
    val error: Color,               //красный
)

data class AppTypography(
    val titleLarge: TextStyle,       //шрифт для заголовков
    val titleNormal: TextStyle,
    val titleSmall: TextStyle,
    val bodyLarge: TextStyle,        //шрифт для больших текстов
    val bodyNormal: TextStyle,
    val bodySmall: TextStyle,
    val labelLarge: TextStyle,       //шрифт для текста внутри компонентов
    val labelNormal: TextStyle,      //шрифт для текста внутри компонентов
    val labelSmall: TextStyle,
)

data class AppShape(
    val small: Shape,
    val medium: Shape,
    val large: Shape,
)

data class AppSize(
    val dp1: Dp,
    val dp2: Dp,
    val dp4: Dp,
    val dp8: Dp,
    val dp10: Dp,
    val dp12: Dp,
    val dp16: Dp,
    val dp24: Dp,
)

val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorsScheme(
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        text = Color.Unspecified,
        onText = Color.Unspecified,
        transparent = Color.Unspecified,
        error = Color.Unspecified,
    )
}

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        titleLarge = TextStyle.Default,
        titleNormal = TextStyle.Default,
        titleSmall = TextStyle.Default,
        bodyLarge = TextStyle.Default,
        bodyNormal = TextStyle.Default,
        bodySmall = TextStyle.Default,
        labelLarge = TextStyle.Default,
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
        dp1 = Dp.Unspecified,
        dp2 = Dp.Unspecified,
        dp4 = Dp.Unspecified,
        dp8 = Dp.Unspecified,
        dp10 = Dp.Unspecified,
        dp12 = Dp.Unspecified,
        dp16 = Dp.Unspecified,
        dp24 = Dp.Unspecified,
    )
}