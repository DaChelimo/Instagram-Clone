package com.da_chelimo.ig_clone.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private val DarkColorScheme = darkColorScheme(
    primary = Black,
    secondary = Black,
    surface = LightBlack,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Color.White,
    background = Black,
    onBackground = Black
)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    secondary = Color.White,
    surface = Color.White,
    onPrimary = Black,
    onSecondary = Black,
    onSurface = Black

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun PreviewDarkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = {
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onPrimary
            ) {
                content()
            }
        }
    )
}

@Composable
fun IGCloneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+ TODO: Change dynamicColor to true
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        ChangeSystemBarColors(
            systemBarColor = if (Firebase.auth.currentUser != null) colorScheme.primary else SignInBlue,
            isDarkTheme = darkTheme,
            view = view
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


@Composable
fun ChangeSystemBarColors(systemBarColor: Color, isDarkTheme: Boolean, view: View) {
    SideEffect {
        val window = (view.context as Activity).window
        val insetsController = WindowCompat.getInsetsController(window, view)
        window.statusBarColor = systemBarColor.toArgb()
        window.navigationBarColor = systemBarColor.toArgb()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.navigationBarDividerColor = systemBarColor.toArgb()
        }
        insetsController.isAppearanceLightStatusBars = !isDarkTheme
        insetsController.isAppearanceLightNavigationBars = !isDarkTheme
    }
}
