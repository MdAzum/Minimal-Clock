package com.azum.clock.prefs

/*
 * Tiny SharedPreferences wrapper so the user's chosen background and
 * clock style survive app restarts. No extra dependency beyond core-ktx.
 */

import android.content.Context
import androidx.core.content.edit

object AzumPreferences {
    private const val PREFS_NAME = "azum_clock_prefs"
    private const val KEY_BACKGROUND_TYPE = "azum_background_type"
    private const val KEY_CUSTOM_COLOR = "azum_custom_background_color"
    private const val KEY_STYLE_INDEX = "azum_clock_style_index"

    private val DEFAULT_CUSTOM_COLOR = 0xFF3F51B5.toInt() // a calm indigo

    fun getBackgroundType(context: Context): String =
        prefs(context).getString(KEY_BACKGROUND_TYPE, "BLACK") ?: "BLACK"

    fun setBackgroundType(context: Context, value: String) {
        prefs(context).edit { putString(KEY_BACKGROUND_TYPE, value) }
    }

    fun getCustomColor(context: Context): Int =
        prefs(context).getInt(KEY_CUSTOM_COLOR, DEFAULT_CUSTOM_COLOR)

    fun setCustomColor(context: Context, color: Int) {
        prefs(context).edit { putInt(KEY_CUSTOM_COLOR, color) }
    }

    fun getStyleIndex(context: Context): Int =
        prefs(context).getInt(KEY_STYLE_INDEX, 0)

    fun setStyleIndex(context: Context, index: Int) {
        prefs(context).edit { putInt(KEY_STYLE_INDEX, index) }
    }

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
}
