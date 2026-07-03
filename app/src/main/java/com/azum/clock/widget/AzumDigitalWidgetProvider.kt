package com.azum.clock.widget

/*
 * Azum Digital Clock Widget
 * Uses android.widget.TextClock inside RemoteViews, which refreshes itself
 * automatically (system-driven), so no manual alarm/refresh scheduling
 * is needed to keep the time accurate.
 */

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.azum.clock.R

class AzumDigitalWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            azumUpdateDigitalWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        fun azumUpdateDigitalWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.azum_widget_digital)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
