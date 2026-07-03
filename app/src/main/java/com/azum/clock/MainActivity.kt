package com.azum.clock

/*
 * Minimal Clock — main entry point.
 * Single full-screen swipeable clock. All settings (background + about)
 * live behind the gear icon, reached from ClockPagerScreen.
 */

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.azum.clock.model.AzumBackgroundType
import com.azum.clock.prefs.AzumPreferences
import com.azum.clock.ui.AzumSettingsDialog
import com.azum.clock.ui.ClockPagerScreen
import com.azum.clock.ui.theme.AzumClockTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Lets the "No Background" clock option reveal the real home-screen wallpaper.
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER)

        setContent {
            AzumClockTheme {
                AzumApp()
            }
        }
    }
}

@Composable
private fun AzumApp() {
    val context = LocalContext.current

    var backgroundType by remember {
        mutableStateOf(AzumBackgroundType.valueOf(AzumPreferences.getBackgroundType(context)))
    }
    var customColor by remember {
        mutableStateOf(AzumPreferences.getCustomColor(context))
    }
    var styleIndex by remember {
        mutableStateOf(AzumPreferences.getStyleIndex(context))
    }
    var showSettings by remember { mutableStateOf(false) }

    ClockPagerScreen(
        backgroundType = backgroundType,
        customColor = customColor,
        initialStyleIndex = styleIndex,
        onStyleIndexChange = {
            styleIndex = it
            AzumPreferences.setStyleIndex(context, it)
        },
        onOpenSettings = { showSettings = true }
    )

    if (showSettings) {
        AzumSettingsDialog(
            backgroundType = backgroundType,
            customColor = customColor,
            onBackgroundTypeChange = {
                backgroundType = it
                AzumPreferences.setBackgroundType(context, it.name)
            },
            onCustomColorChange = {
                customColor = it
                AzumPreferences.setCustomColor(context, it)
            },
            onDismiss = { showSettings = false }
        )
    }
}
