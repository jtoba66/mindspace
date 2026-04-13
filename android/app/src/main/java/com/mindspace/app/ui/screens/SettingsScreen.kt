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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindspace.app.ui.components.MindSpaceBackdrop
import com.mindspace.app.ui.components.mindSpaceCardColor
import com.mindspace.app.ui.theme.PrimaryFixed
import com.mindspace.app.ui.theme.SecondaryFixed
import com.mindspace.app.ui.viewmodel.SessionViewModel

@Composable
fun SettingsScreen(viewModel: SessionViewModel) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    MindSpaceBackdrop(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                top = 20.dp,
                bottom = 112.dp
            )
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Tailor your space for maximum peace.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            item { ProfileCard() }

            item {
                SettingsStackCard(
                    title = "Private Mode",
                    subtitle = "Keep reflections out of your journey and saved history.",
                    icon = Icons.Default.Lock,
                    iconBackground = PrimaryFixed,
                    iconTint = MaterialTheme.colorScheme.primary,
                    trailing = {
                        Switch(
                            checked = viewModel.isPrivateMode,
                            onCheckedChange = { viewModel.isPrivateMode = it },
                            colors = settingsSwitchColors()
                        )
                    }
                )
            }

            item {
                SettingsStackCard(
                    title = "Gentle Check Ins",
                    subtitle = "Soft nudges for daily reflections.",
                    icon = Icons.Default.Notifications,
                    iconBackground = SecondaryFixed,
                    iconTint = MaterialTheme.colorScheme.secondary,
                    trailing = {
                        Switch(
                            checked = true,
                            onCheckedChange = { },
                            colors = settingsSwitchColors()
                        )
                    }
                )
            }

            item {
                SettingsStackCard(
                    title = "Appearance",
                    subtitle = "Choose the app appearance that feels best for your space.",
                    icon = Icons.Default.DarkMode,
                    iconBackground = MaterialTheme.colorScheme.surfaceContainerHigh,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    trailing = {
                        Switch(
                            checked = viewModel.isDarkMode,
                            onCheckedChange = { viewModel.isDarkMode = it },
                            colors = settingsSwitchColors()
                        )
                    }
                )
            }

            item {
                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Delete All Data",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp)
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
            shape = RoundedCornerShape(28.dp),
            containerColor = Color.White
        )
    }
}

@Composable
private fun ProfileCard() {
    Surface(
        shape = RoundedCornerShape(28.dp),
        color = mindSpaceCardColor(lightAlpha = 0.94f),
        shadowElevation = 10.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.16f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(24.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = "Alex Johnson",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Member since March 2024",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SettingsStackCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconBackground: Color,
    iconTint: Color,
    trailing: @Composable (() -> Unit)? = null
) {
    Surface(
        shape = RoundedCornerShape(28.dp),
        color = mindSpaceCardColor(lightAlpha = 0.94f),
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(iconBackground, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (trailing != null) {
                Spacer(modifier = Modifier.width(10.dp))
                trailing()
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
