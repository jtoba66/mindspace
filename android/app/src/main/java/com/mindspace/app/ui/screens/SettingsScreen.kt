package com.mindspace.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindspace.app.ui.components.MindSpaceBackdrop
import com.mindspace.app.ui.components.mindSpaceCardBorderColor
import com.mindspace.app.ui.components.mindSpaceCardColor
import com.mindspace.app.ui.components.squishyClick
import com.mindspace.app.ui.theme.SecondaryFixed
import com.mindspace.app.ui.theme.TertiaryFixed
import com.mindspace.app.ui.viewmodel.SessionViewModel

@Composable
fun SettingsScreen(viewModel: SessionViewModel) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    MindSpaceBackdrop(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                top = 24.dp,
                bottom = 112.dp
            )
        ) {
            item {
                Text(
                    text = "Settings",
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Section 1: Profile Hero
            item { ProfileHeroSection() }

            // Section 3: Main Settings Group
            item {
                SettingsGroupCard {
                    SettingsRowItem(
                        icon = Icons.Default.Lock,
                        title = "Private Mode",
                        subtitle = "Keep reflections out of your journey",
                        trailing = {
                            Switch(
                                checked = viewModel.isPrivateMode,
                                onCheckedChange = { viewModel.isPrivateMode = it },
                                colors = settingsSwitchColors()
                            )
                        }
                    )
                    SettingsRowItem(
                        icon = Icons.Default.Notifications,
                        title = "Gentle Check Ins",
                        subtitle = "Soft nudges for daily reflections",
                        trailing = {
                            Switch(
                                checked = true,
                                onCheckedChange = { },
                                colors = settingsSwitchColors()
                            )
                        }
                    )
                    SettingsRowItem(
                        icon = Icons.Default.Palette,
                        title = "Appearance",
                        subtitle = "Choose the theme that feels best",
                        trailing = {
                            Switch(
                                checked = viewModel.isDarkMode,
                                onCheckedChange = { viewModel.isDarkMode = it },
                                colors = settingsSwitchColors()
                            )
                        }
                    )
                }
            }

            // Section 4: Support Banner
            item { SupportMindSpaceBanner() }

            // Section 5: Misc/Danger
            item {
                SettingsGroupCard {
                    SettingsRowItem(
                        icon = Icons.Default.DeleteForever,
                        title = "Delete All Data",
                        subtitle = "Permanently remove your saved plans",
                        iconTint = MaterialTheme.colorScheme.error,
                        onClick = { showDeleteDialog = true }
                    )
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete all data?") },
            text = {
                Text("This will permanently remove your saved sessions and plans from this device.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteData()
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete everything")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(32.dp),
            containerColor = Color.White
        )
    }
}

@Composable
private fun ProfileHeroSection() {
    Surface(
        shape = RoundedCornerShape(32.dp),
        color = mindSpaceCardColor(lightAlpha = 0.96f),
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape)
                        .border(4.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(50.dp)
                    )
                }
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.BottomEnd).size(32.dp),
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("G", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "ALEX JOHNSON",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.2.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "alex.johnson@example.com",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Member since March 2024",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun GoalSectionLayout() {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = mindSpaceCardColor(lightAlpha = 0.6f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Adjust,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "My focus",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Mental Clarity", "Daily Peace", "Self Growth", "Quiet Mind").forEachIndexed { index, title ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (index == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        contentColor = if (index == 0) Color.White else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.squishyClick { }
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsGroupCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = mindSpaceCardColor(lightAlpha = 0.94f),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            content = content
        )
    }
}

@Composable
private fun SettingsRowItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    trailing: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    val modifier = if (onClick != null) Modifier.squishyClick(onClick = onClick) else Modifier
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (trailing != null) {
            trailing()
        } else if (onClick != null) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

@Composable
private fun SupportMindSpaceBanner() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().height(72.dp),
        shadowElevation = 6.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFE040A0), // Primary
                            Color(0xFFFF80AB)  // Lighter pink
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Diamond, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Support MindSpace",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun settingsSwitchColors() = SwitchDefaults.colors(
    checkedThumbColor = Color.White,
    checkedTrackColor = MaterialTheme.colorScheme.primary,
    uncheckedThumbColor = Color.White,
    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
    uncheckedBorderColor = MaterialTheme.colorScheme.outlineVariant
)
