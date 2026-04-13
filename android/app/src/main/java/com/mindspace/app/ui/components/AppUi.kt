package com.mindspace.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindspace.app.ui.theme.Primary
import com.mindspace.app.ui.theme.PrimaryFixed
import com.mindspace.app.ui.theme.SecondaryFixed
import com.mindspace.app.ui.theme.TertiaryFixed

@Composable
fun MindSpaceBackdrop(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        DecorativeOrb(
            color = PrimaryFixed,
            size = 220.dp,
            alpha = 0.35f,
            alignment = Alignment.TopStart,
            offsetX = 32.dp,
            offsetY = 80.dp
        )
        DecorativeOrb(
            color = TertiaryFixed,
            size = 240.dp,
            alpha = 0.28f,
            alignment = Alignment.BottomEnd,
            offsetX = (-18).dp,
            offsetY = (-90).dp
        )
        DecorativeOrb(
            color = SecondaryFixed,
            size = 180.dp,
            alpha = 0.18f,
            alignment = Alignment.CenterEnd,
            offsetX = 54.dp,
            offsetY = 0.dp
        )
        content()
    }
}

@Composable
fun MindSpaceSectionHeader(
    title: String,
    tint: Color,
    symbol: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = symbol,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                fontSize = 18.sp
            ),
            color = tint
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun MindSpaceQuotePanel(
    quote: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        color = Color.Transparent,
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Primary.copy(alpha = 0.95f),
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f),
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.82f)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            DecorativeOrb(
                color = Color.White,
                size = 96.dp,
                alpha = 0.12f,
                alignment = Alignment.TopEnd,
                offsetX = 18.dp,
                offsetY = (-18).dp
            )
            DecorativeOrb(
                color = Color.White,
                size = 120.dp,
                alpha = 0.08f,
                alignment = Alignment.BottomStart,
                offsetX = (-26).dp,
                offsetY = 30.dp
            )
            Text(
                text = "\"$quote\"",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    lineHeight = 26.sp
                ),
                color = Color.White
            )
        }
    }
}

@Composable
fun MiniAvatarStack(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((-10).dp)
    ) {
        listOf(
            Primary to "J",
            MaterialTheme.colorScheme.secondary to "M",
            MaterialTheme.colorScheme.tertiary to "A"
        ).forEach { (color, label) ->
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .border(2.dp, Color.White, CircleShape)
                    .background(color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun mindSpaceCardColor(
    lightAlpha: Float = 0.92f,
    darkAlpha: Float = 0.92f
): Color {
    val isDarkMode = MaterialTheme.colorScheme.background.luminance() < 0.5f
    return if (isDarkMode) {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = darkAlpha)
    } else {
        Color.White.copy(alpha = lightAlpha)
    }
}

@Composable
fun mindSpaceCardBorderColor(
    lightAlpha: Float = 0.4f,
    darkAlpha: Float = 0.82f
): Color {
    val isDarkMode = MaterialTheme.colorScheme.background.luminance() < 0.5f
    return MaterialTheme.colorScheme.outlineVariant.copy(
        alpha = if (isDarkMode) darkAlpha else lightAlpha
    )
}

@Composable
private fun BoxScope.DecorativeOrb(
    color: Color,
    size: Dp,
    alpha: Float,
    alignment: Alignment,
    offsetX: Dp,
    offsetY: Dp
) {
    Box(
        modifier = Modifier
            .align(alignment)
            .offset(x = offsetX, y = offsetY)
            .size(size)
            .alpha(alpha)
            .background(color, CircleShape)
    )
}
