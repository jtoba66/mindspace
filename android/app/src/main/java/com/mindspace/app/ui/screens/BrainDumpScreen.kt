package com.mindspace.app.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.mindspace.app.audio.AudioRecorder
import com.mindspace.app.audio.VoiceStreamManager
import com.mindspace.app.ui.components.MindSpaceBackdrop
import com.mindspace.app.ui.components.mindSpaceCardBorderColor
import com.mindspace.app.ui.components.mindSpaceCardColor
import com.mindspace.app.ui.theme.SecondaryFixed
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BrainDumpScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    onNext: (String) -> Unit,
    onCrisis: () -> Unit,
    modifier: Modifier = Modifier
) {
    var textState by rememberSaveable { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }
    var interimTranscript by remember { mutableStateOf("") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var hasMicPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val streamManager = remember {
        VoiceStreamManager { transcript, isFinal ->
            if (isFinal) {
                textState += if (textState.isEmpty()) transcript else " $transcript"
                interimTranscript = ""
            } else {
                interimTranscript = transcript
            }
        }
    }
    val recorder = remember { AudioRecorder(context) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasMicPermission = isGranted
        if (isGranted) {
            isRecording = true
            streamManager.connect()
            coroutineScope.launch {
                recorder.startRecording(File(context.cacheDir, "temp.pcm"))
            }
        }
    }

    MindSpaceBackdrop(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = if (MaterialTheme.colorScheme.background.luminance() < 0.5f) {
                        mindSpaceCardColor(lightAlpha = 0.9f)
                    } else {
                        SecondaryFixed.copy(alpha = 0.9f)
                    },
                    shape = CircleShape,
                    shadowElevation = 6.dp
                ) {
                    Text(
                        text = "Safe Space",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                        color = if (MaterialTheme.colorScheme.background.luminance() < 0.5f) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                }

                Surface(
                    onClick = onCrisis,
                    color = mindSpaceCardColor(lightAlpha = 0.7f),
                    shape = CircleShape,
                    shadowElevation = 2.dp,
                    border = BorderStroke(
                        1.dp,
                        mindSpaceCardBorderColor(lightAlpha = 0.45f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MedicalServices,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Overwhelmed?",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            with(sharedTransitionScope) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .sharedElement(
                            state = rememberSharedContentState(key = "input_card"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 2.dp, top = 10.dp, bottom = 28.dp)
                            .width(4.dp)
                            .fillMaxHeight()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.55f),
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                                    )
                                ),
                                shape = CircleShape
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 22.dp)
                    ) {
                        if (isRecording || !hasMicPermission) {
                            Surface(
                                color = Color.White.copy(alpha = 0.65f),
                                shape = CircleShape
                            ) {
                                Text(
                                    text = when {
                                        isRecording -> "I'm listening..."
                                        else -> "Tap the mic to grant permission."
                                    },
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        val greyColor = MaterialTheme.colorScheme.outline
                        val interimVisualTransformation = VisualTransformation { text ->
                            val annotatedString = buildAnnotatedString {
                                append(text.text)
                                if (isRecording && interimTranscript.isNotEmpty()) {
                                    withStyle(SpanStyle(color = greyColor)) {
                                        append((if (text.text.isEmpty()) "" else " ") + interimTranscript)
                                    }
                                }
                            }
                            val offsetMapping = object : OffsetMapping {
                                override fun originalToTransformed(offset: Int): Int = offset
                                override fun transformedToOriginal(offset: Int): Int =
                                    if (offset <= text.text.length) offset else text.text.length
                            }
                            TransformedText(annotatedString, offsetMapping)
                        }

                        Box(modifier = Modifier.fillMaxSize()) {
                            if (textState.isEmpty() && (!isRecording || interimTranscript.isEmpty())) {
                                Text(
                                    text = "What's weighing on your mind? Just start typing...",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp, end = 12.dp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.36f),
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Medium,
                                        lineHeight = 40.sp
                                    )
                                )
                            }

                            TextField(
                                value = textState,
                                onValueChange = { if (!isRecording) textState = it },
                                modifier = Modifier.fillMaxSize(),
                                readOnly = isRecording,
                                visualTransformation = interimVisualTransformation,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    cursorColor = MaterialTheme.colorScheme.primary
                                ),
                                textStyle = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = 40.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                RecordingButton(
                    isRecording = isRecording,
                    onClick = {
                        if (isRecording) {
                            isRecording = false
                            recorder.stopRecording()
                            streamManager.disconnect()
                            if (interimTranscript.isNotEmpty()) {
                                textState += (if (textState.isEmpty()) "" else " ") + interimTranscript
                                interimTranscript = ""
                            }
                        } else if (hasMicPermission) {
                            isRecording = true
                            streamManager.connect()
                            coroutineScope.launch {
                                recorder.startRecording(File(context.cacheDir, "temp.pcm"))
                            }
                        } else {
                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(96.dp))
        }

        if (textState.isNotBlank() && !isRecording) {
            Button(
                onClick = { onNext(textState) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 112.dp)
                    .widthIn(min = 220.dp)
                    .wrapContentWidth()
                    .height(56.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Done reflecting",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun RecordingButton(
    isRecording: Boolean,
    onClick: () -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        if (isRecording) {
            val infiniteTransition = rememberInfiniteTransition(label = "mic_glow")
            val glowScale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.8f,
                animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Restart),
                label = "scale"
            )
            val glowAlpha by infiniteTransition.animateFloat(
                initialValue = 0.35f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Restart),
                label = "alpha"
            )
            Box(
                modifier = Modifier
                    .size(62.dp)
                    .scale(glowScale)
                    .alpha(glowAlpha)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
            )
        }

        Surface(
            onClick = onClick,
            shape = CircleShape,
            color = if (isRecording) {
                MaterialTheme.colorScheme.primary
            } else {
                Color.White.copy(alpha = 0.72f)
            },
            shadowElevation = 10.dp,
            border = if (isRecording) null else BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)
            )
        ) {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Voice input",
                tint = if (isRecording) Color.White else MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(18.dp)
            )
        }
    }
}
