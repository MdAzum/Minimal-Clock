package com.azum.clock.widget

/*
 * Azum Analog Clock Widget
 * Uses the platform's built-in AnalogClock view inside RemoteViews, which
 * ticks on its own without any manual refresh logic.
 */

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.azum.clock.R

class AzumAnalogWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            azumUpdateAnalogWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        fun azumUpdateAnalogWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.azum_widget_analog)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
