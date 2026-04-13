package com.mindspace.app.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesomeMotion
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindspace.app.data.model.PlanSection
import com.mindspace.app.ui.components.MindSpaceBackdrop
import com.mindspace.app.ui.components.MindSpaceQuotePanel
import com.mindspace.app.ui.components.MindSpaceSectionHeader
import com.mindspace.app.ui.components.mindSpaceCardBorderColor
import com.mindspace.app.ui.components.mindSpaceCardColor
import com.mindspace.app.ui.theme.PrimaryFixed
import com.mindspace.app.ui.theme.SecondaryFixed
import com.mindspace.app.ui.theme.TertiaryFixed
import com.mindspace.app.ui.viewmodel.SessionUiState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DashboardScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    uiState: SessionUiState,
    onStartReflection: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState !is SessionUiState.Success) {
        MindSpaceBackdrop(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shadowElevation = 8.dp
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesomeMotion,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(26.dp).size(34.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Nothing here yet",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Whenever you're ready to let it out, your plan will show up here — sorted and simple.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 280.dp)
                )
                Spacer(modifier = Modifier.height(28.dp))
                Button(
                    onClick = onStartReflection,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Start a Reflection",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
        return
    }

    val plan = uiState.response

    MindSpaceBackdrop(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                top = 20.dp,
                bottom = 112.dp
            )
        ) {
            item {
                with(sharedTransitionScope) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp)
                            .sharedElement(
                                state = rememberSharedContentState(key = "input_card"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                    ) {
                        Text(
                            text = "Clarity Dashboard",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = plan.header,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            itemsIndexed(plan.sections) { index, section ->
                when (index) {
                    0 -> FocusSection(section)
                    1 -> GentleStepsSection(section)
                    else -> LaterSection(section)
                }
            }

            item {
                MindSpaceQuotePanel(
                    quote = plan.closing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun FocusSection(section: PlanSection) {
    MindSpaceSectionHeader(
        title = section.title,
        tint = MaterialTheme.colorScheme.primary,
        symbol = "!"
    )
    Spacer(modifier = Modifier.height(12.dp))

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        section.items.forEachIndexed { index, item ->
            val style = if (index % 2 == 0) {
                FocusCardStyle(
                    background = PrimaryFixed.copy(alpha = 0.72f),
                    border = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                    badgeColor = MaterialTheme.colorScheme.primary,
                    badgeText = if (index == 0) "URGENT" else "FOCUS",
                    iconTint = MaterialTheme.colorScheme.primary
                )
            } else {
                FocusCardStyle(
                    background = SecondaryFixed.copy(alpha = 0.72f),
                    border = MaterialTheme.colorScheme.secondary.copy(alpha = 0.14f),
                    badgeColor = MaterialTheme.colorScheme.secondary,
                    badgeText = "HIGH IMPACT",
                    iconTint = MaterialTheme.colorScheme.secondary
                )
            }
            FocusCard(item = item, style = style)
        }
    }
}

@Composable
private fun GentleStepsSection(section: PlanSection) {
    MindSpaceSectionHeader(
        title = section.title,
        tint = MaterialTheme.colorScheme.tertiary,
        symbol = "⚡"
    )
    Spacer(modifier = Modifier.height(12.dp))

    if (section.items.isEmpty()) return

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = TertiaryFixed.copy(alpha = 0.62f),
            shadowElevation = 6.dp,
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Mail,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = section.items.first(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Short and soft.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            section.items.drop(1).take(2).forEach { item ->
                CompactStepCard(item = item)
            }
            if (section.items.size == 1) {
                CompactStepCard(item = "You're already keeping this gentle.")
            }
        }
    }
}

@Composable
private fun LaterSection(section: PlanSection) {
    MindSpaceSectionHeader(
        title = section.title,
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
        symbol = "◔"
    )
    Spacer(modifier = Modifier.height(12.dp))

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        section.items.forEachIndexed { index, item ->
            val icon = when (index % 3) {
                0 -> Icons.Default.Bedtime
                1 -> Icons.Default.WaterDrop
                else -> Icons.Default.WbTwilight
            }

            Surface(
                shape = RoundedCornerShape(24.dp),
                color = mindSpaceCardColor(lightAlpha = 0.68f),
                border = BorderStroke(
                    1.dp,
                    mindSpaceCardBorderColor(lightAlpha = 0.4f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(
                                MaterialTheme.colorScheme.surfaceContainer,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "This can wait until later.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.DragIndicator,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun FocusCard(
    item: String,
    style: FocusCardStyle
) {
    Surface(
        shape = RoundedCornerShape(26.dp),
        color = style.background,
        shadowElevation = 8.dp,
        border = BorderStroke(1.dp, style.border)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = style.badgeColor,
                    shape = CircleShape
                ) {
                    Text(
                        text = style.badgeText,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.6.sp),
                        color = Color.White
                    )
                }
                Icon(
                    imageVector = Icons.Default.PriorityHigh,
                    contentDescription = null,
                    tint = style.iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = item,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Focus on the core message before adding more weight.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CompactStepCard(item: String) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = mindSpaceCardColor(lightAlpha = 0.76f),
        shadowElevation = 4.dp,
        border = BorderStroke(1.dp, mindSpaceCardBorderColor(lightAlpha = 0.4f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Text(
                text = item,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = "2m task",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private data class FocusCardStyle(
    val background: Color,
    val border: Color,
    val badgeColor: Color,
    val badgeText: String,
    val iconTint: Color
)
