package com.azum.clock.ui

/*
 * Single settings surface reachable via the gear icon on the clock screen.
 * Holds Background options and the About/developer info section — this is
 * the only other UI in the app besides the clock itself, keeping the
 * interface minimal.
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.azum.clock.model.AzumAboutInfo
import com.azum.clock.model.AzumBackgroundType

@Composable
fun AzumSettingsDialog(
    backgroundType: AzumBackgroundType,
    customColor: Int,
    onBackgroundTypeChange: (AzumBackgroundType) -> Unit,
    onCustomColorChange: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var showColorPicker by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .widthIn(max = 340.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Settings", style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.height(16.dp))
                Text("Background", style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AzumSwatch(
                        color = Color.White,
                        selected = backgroundType == AzumBackgroundType.WHITE,
                        needsBorder = true
                    ) { onBackgroundTypeChange(AzumBackgroundType.WHITE) }

                    AzumSwatch(
                        color = Color.Black,
                        selected = backgroundType == AzumBackgroundType.BLACK
                    ) { onBackgroundTypeChange(AzumBackgroundType.BLACK) }

                    AzumNoneSwatch(
                        selected = backgroundType == AzumBackgroundType.NONE
                    ) { onBackgroundTypeChange(AzumBackgroundType.NONE) }

                    AzumSwatch(
                        color = Color(customColor),
                        selected = backgroundType == AzumBackgroundType.CUSTOM,
                        showEditMark = true
                    ) {
                        onBackgroundTypeChange(AzumBackgroundType.CUSTOM)
                        showColorPicker = true
                    }
                }

                Spacer(Modifier.height(24.dp))
                HorizontalDivider()
                Spacer(Modifier.height(20.dp))

                Text("About", style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(8.dp))
                AzumAboutRow("Developer", AzumAboutInfo.DEV_NAME)
                AzumAboutRow("App", "Minimal Clock")
                AzumAboutRow("Version", AzumAboutInfo.APP_VERSION)
                AzumAboutRow("Email", AzumAboutInfo.EMAIL)
                AzumAboutRow("Website", AzumAboutInfo.WEBSITE)

                Spacer(Modifier.height(10.dp))
                Text(
                    AzumAboutInfo.TAGLINE,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "No ads. No tracking. No account required.",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(Modifier.height(16.dp))
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Done")
                }
            }
        }
    }

    if (showColorPicker) {
        AzumCustomColorDialog(
            initialColor = customColor,
            onColorSelected = {
                onCustomColorChange(it)
                showColorPicker = false
            },
            onDismiss = { showColorPicker = false }
        )
    }
}

@Composable
private fun AzumAboutRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun AzumSwatch(
    color: Color,
    selected: Boolean,
    needsBorder: Boolean = false,
    showEditMark: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(color, CircleShape)
            .then(
                if (needsBorder) Modifier.border(1.dp, Color.LightGray, CircleShape)
                else Modifier
            )
            .then(
                if (selected) Modifier.border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                else Modifier
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (showEditMark) {
            Text("\u270E", fontSize = 14.sp, color = azumReadableTextColor(color, AzumBackgroundType.CUSTOM))
        }
    }
}

@Composable
private fun AzumNoneSwatch(selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color.Transparent, CircleShape)
            .border(1.dp, Color.LightGray, CircleShape)
            .then(
                if (selected) Modifier.border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                else Modifier
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text("\u2205", fontSize = 16.sp, color = Color.Gray) // empty-set symbol = "none"
    }
}

@Composable
private fun AzumCustomColorDialog(
    initialColor: Int,
    onColorSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var red by remember { mutableStateOf(((initialColor shr 16) and 0xFF).toFloat()) }
    var green by remember { mutableStateOf(((initialColor shr 8) and 0xFF).toFloat()) }
    var blue by remember { mutableStateOf((initialColor and 0xFF).toFloat()) }

    val previewColor = Color(red / 255f, green / 255f, blue / 255f)

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .widthIn(max = 320.dp)
            ) {
                Text("Custom Background Color", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(previewColor, RoundedCornerShape(12.dp))
                )
                Spacer(Modifier.height(16.dp))
                AzumColorSlider("Red", red) { red = it }
                AzumColorSlider("Green", green) { green = it }
                AzumColorSlider("Blue", blue) { blue = it }
                Spacer(Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        val colorInt = (0xFF shl 24) or
                            (red.toInt() shl 16) or
                            (green.toInt() shl 8) or
                            blue.toInt()
                        onColorSelected(colorInt)
                    }) { Text("Apply") }
                }
            }
        }
    }
}

@Composable
private fun AzumColorSlider(label: String, value: Float, onValueChange: (Float) -> Unit) {
    Column {
        Text("$label: ${value.toInt()}", fontSize = 12.sp)
        Slider(value = value, onValueChange = onValueChange, valueRange = 0f..255f)
    }
}
