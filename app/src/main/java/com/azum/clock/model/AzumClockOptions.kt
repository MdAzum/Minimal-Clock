package com.azum.clock.model

/*
 * Options for how the clock screen can look.
 * AzumClockStyle now represents a full swipeable "style" (font + layout
 * treatment), selected by swiping left/right on the main clock screen.
 */

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

enum class AzumBackgroundType {
    WHITE,
    BLACK,
    NONE,   // transparent — reveals the device's home-screen wallpaper
    CUSTOM  // user-picked solid color
}

enum class AzumClockStyle(
    val displayName: String,
    val fontFamily: FontFamily,
    val fontWeight: FontWeight,
    val letterSpacingSp: Float = 0f
) {
    MINIMAL("Minimal", FontFamily.Default, FontWeight.Light),
    BOLD("Bold", FontFamily.Default, FontWeight.Bold),
    MONO("Mono", FontFamily.Monospace, FontWeight.Normal),
    SERIF("Serif", FontFamily.Serif, FontWeight.Normal),
    COMPACT("Compact", FontFamily.SansSerif, FontWeight.Medium, -1.5f)
}
