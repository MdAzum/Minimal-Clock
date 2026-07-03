package com.azum.clock.ui

/*
 * The main (and only) screen: a full-screen swipeable clock.
 * Swipe left/right to change clock style. No branding text here by
 * design — ownership lives in the source code and Settings > About.
 */

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azum.clock.model.AzumBackgroundType
import com.azum.clock.model.AzumClockStyle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClockPagerScreen(
    backgroundType: AzumBackgroundType,
    customColor: Int,
    initialStyleIndex: Int,
    onStyleIndexChange: (Int) -> Unit,
    onOpenSettings: () -> Unit
) {
    val styles = AzumClockStyle.values()

    val backgroundColor = when (backgroundType) {
        AzumBackgroundType.WHITE -> Color.White
        AzumBackgroundType.BLACK -> Color.Black
        AzumBackgroundType.NONE -> Color.Transparent
        AzumBackgroundType.CUSTOM -> Color(customColor)
    }
    val textColor = azumReadableTextColor(backgroundColor, backgroundType)

    val pagerState = rememberPagerState(
        initialPage = initialStyleIndex.coerceIn(0, styles.size - 1)
    ) { styles.size }

    LaunchedEffect(pagerState.currentPage) {
        onStyleIndexChange(pagerState.currentPage)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AzumClockStyleFace(style = styles[page], textColor = textColor)
            }
        }

        // Page indicator dots
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(bottom = 28.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            styles.indices.forEach { index ->
                val selected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .size(if (selected) 8.dp else 6.dp)
                        .background(
                            textColor.copy(alpha = if (selected) 0.9f else 0.3f),
                            CircleShape
                        )
                )
            }
        }

        IconButton(
            onClick = onOpenSettings,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(12.dp)
        ) {
            Text("\u2699", color = textColor, fontSize = 22.sp) // gear symbol
        }
    }
}

@Composable
private fun AzumClockStyleFace(style: AzumClockStyle, textColor: Color) {
    var currentTime by remember { mutableStateOf(azumFormattedTime(style)) }
    var currentDate by remember { mutableStateOf(azumFormattedDate()) }

    LaunchedEffect(style) {
        while (true) {
            currentTime = azumFormattedTime(style)
            currentDate = azumFormattedDate()
            kotlinx.coroutines.delay(1000L)
        }
    }

    if (style == AzumClockStyle.COMPACT) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = textColor.copy(alpha = 0.08f)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    currentDate,
                    color = textColor.copy(alpha = 0.7f),
                    fontSize = 16.sp,
                    fontFamily = style.fontFamily
                )
                Text(
                    currentTime,
                    color = textColor,
                    fontSize = 60.sp,
                    fontWeight = style.fontWeight,
                    fontFamily = style.fontFamily,
                    letterSpacing = style.letterSpacingSp.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = currentTime,
                color = textColor,
                fontSize = 76.sp,
                fontWeight = style.fontWeight,
                fontFamily = style.fontFamily,
                letterSpacing = style.letterSpacingSp.sp
            )
            Text(
                text = currentDate,
                color = textColor.copy(alpha = 0.7f),
                fontSize = 18.sp,
                fontFamily = style.fontFamily,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/** Picks readable text color for whatever background is active. */
internal fun azumReadableTextColor(background: Color, type: AzumBackgroundType): Color {
    return when (type) {
        AzumBackgroundType.WHITE -> Color.Black
        AzumBackgroundType.BLACK -> Color.White
        AzumBackgroundType.NONE -> Color.White
        AzumBackgroundType.CUSTOM -> {
            val luminance = 0.299 * background.red + 0.587 * background.green + 0.114 * background.blue
            if (luminance > 0.6) Color.Black else Color.White
        }
    }
}

private fun azumFormattedTime(style: AzumClockStyle): String {
    val pattern = if (style == AzumClockStyle.MONO) "HH:mm:ss" else "HH:mm"
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date())
}

private fun azumFormattedDate(): String =
    SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(Date())
