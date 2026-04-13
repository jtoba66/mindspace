package com.mindspace.app.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindspace.app.ui.components.MindSpaceBackdrop
import com.mindspace.app.ui.components.MiniAvatarStack
import com.mindspace.app.ui.components.mindSpaceCardColor
import com.mindspace.app.ui.theme.PrimaryFixed
import com.mindspace.app.ui.theme.SecondaryFixed
import com.mindspace.app.ui.theme.TertiaryFixed

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun EnergyCheckScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    onSelectionChanged: (String) -> Unit,
    onReady: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current

    MindSpaceBackdrop(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            Surface(
                color = PrimaryFixed,
                shape = RoundedCornerShape(26.dp),
                shadowElevation = 10.dp
            ) {
                Icon(
                    imageVector = Icons.Default.ElectricBolt,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(16.dp).size(34.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "How is your energy right now?",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 300.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Let's check in with your battery. Your MindSpace sessions will adapt to meet you where you are.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 320.dp)
            )

            Spacer(modifier = Modifier.height(34.dp))

            Column(
                modifier = Modifier.widthIn(max = 380.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                EnergyOption(
                    title = "Low",
                    subtitle = "I need something gentle and restorative.",
                    icon = Icons.Default.BatteryAlert,
                    iconBgColor = TertiaryFixed,
                    iconTintColor = MaterialTheme.colorScheme.tertiary,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onSelectionChanged("low")
                        onReady()
                    }
                )

                EnergyOption(
                    title = "Steady",
                    subtitle = "I'm feeling balanced and ready to focus.",
                    icon = Icons.Default.LightMode,
                    iconBgColor = SecondaryFixed,
                    iconTintColor = MaterialTheme.colorScheme.secondary,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onSelectionChanged("steady")
                        onReady()
                    }
                )

                EnergyOption(
                    title = "Ready",
                    subtitle = "I've got high vibes and big energy!",
                    icon = Icons.Default.AutoAwesome,
                    iconBgColor = PrimaryFixed,
                    iconTintColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onSelectionChanged("ready")
                        onReady()
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            MiniAvatarStack()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "DYNAMIC PLANNING",
                style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 1.8.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "\"Taking a moment to listen to your body is the first step to a better day.\"",
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 280.dp)
            )
            Spacer(modifier = Modifier.height(88.dp))
        }
    }
}

@Composable
private fun EnergyOption(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconBgColor: Color,
    iconTintColor: Color,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val rotation = if (isPressed) 10f else 0f

    Surface(
        onClick = {
            isPressed = true
            onClick()
        },
        shape = RoundedCornerShape(30.dp),
        color = mindSpaceCardColor(lightAlpha = 0.9f),
        shadowElevation = 10.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .background(iconBgColor, RoundedCornerShape(18.dp))
                    .rotate(rotation),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTintColor,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.size(18.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
