package com.mindspace.app.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindspace.app.ui.components.MindSpaceBackdrop
import com.mindspace.app.ui.components.mindSpaceCardColor
import com.mindspace.app.ui.theme.SecondaryFixed
import com.mindspace.app.ui.theme.TertiaryFixed

@Composable
fun CrisisSupportScreen(onClose: () -> Unit) {
    val context = LocalContext.current
    var groundingStep by remember { mutableStateOf(0) }
    val groundingSteps = listOf(
        "Look around. Name 5 things you can see.",
        "Acknowledge 4 things you can touch.",
        "Name 3 things you can hear.",
        "Name 2 things you can smell.",
        "Focus on 1 thing you can taste."
    )

    MindSpaceBackdrop(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    onClick = onClose,
                    color = Color.White.copy(alpha = 0.8f),
                    shape = CircleShape,
                    shadowElevation = 4.dp
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "It's okay.",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "It's completely fine if you don't have the words right now. We can still clear some space.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(26.dp))

            SupportOptionCard(
                title = "Breathe with me",
                subtitle = "Just 60 seconds of quiet",
                icon = Icons.Default.Air,
                background = TertiaryFixed.copy(alpha = 0.55f),
                iconTint = MaterialTheme.colorScheme.tertiary,
                onClick = { groundingStep = 0 }
            )
            Spacer(modifier = Modifier.height(12.dp))
            SupportOptionCard(
                title = "Just talk",
                subtitle = "Call or text support below",
                icon = Icons.Default.Mic,
                background = SecondaryFixed.copy(alpha = 0.55f),
                iconTint = MaterialTheme.colorScheme.secondary,
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:988"))
                    context.startActivity(intent)
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            SupportOptionCard(
                title = "Come back to this",
                subtitle = "Close the app and try again tomorrow",
                icon = Icons.Default.WbTwilight,
                background = Color.White.copy(alpha = 0.85f),
                iconTint = MaterialTheme.colorScheme.onSurfaceVariant,
                onClick = onClose,
                bordered = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                shape = RoundedCornerShape(28.dp),
                color = mindSpaceCardColor(lightAlpha = 0.88f),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Professional help",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ResourceRow(
                        title = "Call 988 Lifeline",
                        icon = Icons.Default.Call,
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:988"))
                            context.startActivity(intent)
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    ResourceRow(
                        title = "Text HOME to 741741",
                        icon = Icons.Default.Textsms,
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:741741?body=HOME"))
                            context.startActivity(intent)
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    ResourceRow(
                        title = "Find a helpline worldwide",
                        icon = Icons.Default.Public,
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://findahelpline.com/"))
                            context.startActivity(intent)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Surface(
                shape = RoundedCornerShape(28.dp),
                color = mindSpaceCardColor(lightAlpha = 0.88f),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "A quick way to center",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    if (groundingStep < groundingSteps.size) {
                        Text(
                            text = groundingSteps[groundingStep],
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Button(
                            onClick = { groundingStep++ },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("I've done that")
                        }
                    } else {
                        Text(
                            text = "Take a deep breath. You're here.",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "You can reset this exercise anytime.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { groundingStep = 0 },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            )
                        ) {
                            Text(
                                text = "Reset exercise",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun SupportOptionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    background: Color,
    iconTint: Color,
    onClick: () -> Unit,
    bordered: Boolean = false
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(28.dp),
        color = if (bordered) {
            mindSpaceCardColor(lightAlpha = 0.85f)
        } else background,
        shadowElevation = 6.dp,
        border = if (bordered) {
            BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
        } else {
            null
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ResourceRow(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
