package com.mindspace.app.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindspace.app.ui.theme.PrimaryFixed
import com.mindspace.app.ui.theme.SecondaryFixed
import com.mindspace.app.ui.theme.TertiaryFixed

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProcessingScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "processing")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    val progressOffset by infiniteTransition.animateFloat(
        initialValue = -0.25f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progressOffset"
    )
    val orbFloat by infiniteTransition.animateFloat(
        initialValue = -12f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(3800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orbFloat"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        TertiaryFixed.copy(alpha = 0.65f),
                        MaterialTheme.colorScheme.background
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 110.dp)
                .size(280.dp)
                .alpha(0.26f)
                .background(PrimaryFixed, CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-120).dp)
                .size(300.dp)
                .alpha(0.22f)
                .background(SecondaryFixed, CircleShape)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 28.dp)
        ) {
            with(sharedTransitionScope) {
                Surface(
                    modifier = Modifier
                        .size(96.dp)
                        .scale(pulseScale)
                        .sharedElement(
                            state = rememberSharedContentState(key = "input_card"),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.95f),
                    shadowElevation = 16.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        BubbleCluster()
                    }
                }
            }

            Spacer(modifier = Modifier.height(38.dp))

            Text(
                text = "Taking a breath...",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "sorting through your thoughts with care.",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(54.dp))

            Box(
                modifier = Modifier
                    .width(170.dp)
                    .height(8.dp)
                    .background(Color.White.copy(alpha = 0.7f), CircleShape)
            ) {
                Box(
                    modifier = Modifier
                        .offset(x = (progressOffset * 115).dp)
                        .width(56.dp)
                        .height(8.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(58.dp))

            Box(
                modifier = Modifier
                    .offset(y = orbFloat.dp)
                    .size(118.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.95f),
                                PrimaryFixed.copy(alpha = 0.8f),
                                TertiaryFixed.copy(alpha = 0.55f)
                            )
                        ),
                        shape = CircleShape
                    )
                    .alpha(0.88f)
            )
        }

        Text(
            text = "MINDSPACE IS LISTENING",
            style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 2.sp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.55f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        )
    }
}

@Composable
private fun BubbleCluster() {
    val primary = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BubbleDot(
            modifier = Modifier
                .size(20.dp)
                .offset(x = 4.dp, y = 2.dp),
            color = primary
        )
        BubbleDot(
            modifier = Modifier
                .size(16.dp)
                .offset(x = 18.dp, y = (-12).dp),
            color = primary.copy(alpha = 0.72f)
        )
        BubbleDot(
            modifier = Modifier
                .size(12.dp)
                .offset(x = (-10).dp, y = 16.dp),
            color = primary.copy(alpha = 0.58f)
        )
    }
}

@Composable
private fun BubbleDot(
    modifier: Modifier,
    color: Color
) {
    Box(
        modifier = modifier.background(color, CircleShape)
    )
}
