package com.azum.clock.widget

/*
 * Azum Icon-style Clock Widget
 * A 1x1 home-screen widget sized and shaped like an app icon, showing a
 * live ticking time. Android does not allow third-party apps to make the
 * real launcher icon update live (that's an OS-reserved behavior, used
 * only by the system Calendar app) — this widget is the practical
 * equivalent: drag it into your app grid next to/instead of the icon and
 * it behaves like a live clock "icon".
 */

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.azum.clock.R

class AzumIconWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.azum_widget_icon)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
