package com.mindspace.app.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindspace.app.data.model.PlanResponse
import com.mindspace.app.ui.components.MindSpaceBackdrop
import com.mindspace.app.ui.components.mindSpaceCardColor
import com.mindspace.app.ui.viewmodel.SessionViewModel

@Composable
fun JourneyScreen(
    viewModel: SessionViewModel,
    onStartReflection: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchJourney()
    }

    val sessions = viewModel.journeyState

    if (sessions.isEmpty()) {
        MindSpaceBackdrop(modifier = Modifier.fillMaxSize()) {
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
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        modifier = Modifier.padding(24.dp).size(36.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Your story starts here",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Every time you reflect, a page gets added. Over time, you'll see how far you've come.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 290.dp)
                )
                Spacer(modifier = Modifier.height(26.dp))
                Button(
                    onClick = onStartReflection,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.SelfImprovement, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Start Your First Reflection")
                }
            }
        }
        return
    }

    val featured = sessions.first()
    val recentSessions = sessions.take(2)
    val olderSessions = sessions.drop(2)

    MindSpaceBackdrop(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                top = 20.dp,
                bottom = 112.dp
            )
        ) {
            item {
                Column(modifier = Modifier.padding(horizontal = 6.dp)) {
                    Text(
                        text = "Your Journey",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Looking back at your clarity journey and energy shifts.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeaturedJourneyCard(
                        session = featured,
                        modifier = Modifier.weight(1.5f)
                    )
                    EnergyHighlightCard(
                        energy = featured.classification.energy,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                SectionLabel(
                    label = "This Week",
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            itemsIndexed(recentSessions) { index, session ->
                JourneyEntryCard(
                    session = session,
                    icon = if (index == 0) Icons.Default.Mood else Icons.Default.BatteryFull,
                    accent = if (index == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
                )
            }

            if (olderSessions.isNotEmpty()) {
                item {
                    SectionLabel(
                        label = "Earlier",
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                itemsIndexed(olderSessions) { index, session ->
                    JourneyEntryCard(
                        session = session,
                        icon = if (index % 2 == 0) Icons.Default.Bedtime else Icons.Default.AutoAwesome,
                        accent = if (index % 2 == 0) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                    )
                }
            }

            item {
                EndOfJourneyCard(onStartReflection = onStartReflection)
            }
        }
    }
}

@Composable
private fun FeaturedJourneyCard(
    session: PlanResponse,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        color = mindSpaceCardColor(lightAlpha = 0.9f),
        shadowElevation = 10.dp
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
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                ) {
                    Text(
                        text = "Latest milestone",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = "Recent",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = session.header,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = session.closing,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 4
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InsightDot(MaterialTheme.colorScheme.tertiary, "↗")
                InsightDot(MaterialTheme.colorScheme.secondary, "✦")
            }
        }
    }
}

@Composable
private fun EnergyHighlightCard(
    energy: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.tertiary,
        shadowElevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = energy.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Most recent energy state in your journey.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.84f)
                )
            }
        }
    }
}

@Composable
private fun JourneyEntryCard(
    session: PlanResponse,
    icon: ImageVector,
    accent: Color
) {
    Surface(
        shape = RoundedCornerShape(26.dp),
        color = mindSpaceCardColor(lightAlpha = 0.88f),
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(accent.copy(alpha = 0.16f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = session.header,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = session.classification.energy.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = session.closing,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

@Composable
private fun EndOfJourneyCard(onStartReflection: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(28.dp),
        color = Color.Transparent,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.8f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outlineVariant
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "The further you walk, the more you see.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(14.dp))
            Button(
                onClick = onStartReflection,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Start New Dump")
            }
        }
    }
}

@Composable
private fun SectionLabel(label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(color, CircleShape)
        )
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 1.2.sp),
            color = color
        )
    }
}

@Composable
private fun InsightDot(color: Color, label: String) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .background(color, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}
