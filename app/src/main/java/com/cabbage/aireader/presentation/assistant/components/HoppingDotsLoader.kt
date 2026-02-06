package com.cabbage.aireader.presentation.assistant.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cabbage.aireader.theme.AIReadingHelperTheme

@Composable
fun HoppingDotsLoader(dotColor: Color, dotSize: Dp, hopHeight: Dp, modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    val hopHeightPx = with(density) { hopHeight.toPx() }
    val infiniteTransition = rememberInfiniteTransition(label = "dots")
    val dotCount = 3

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (i in 0 until dotCount) {
            val dy by
                infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 0f,
                    animationSpec =
                        infiniteRepeatable(
                            animation =
                                keyframes {
                                    durationMillis = 600
                                    0f at 0 using LinearOutSlowInEasing
                                    -hopHeightPx at 200 using LinearOutSlowInEasing
                                    0f at 400 using FastOutLinearInEasing
                                    0f at 600
                                },
                            repeatMode = RepeatMode.Restart,
                            initialStartOffset = StartOffset(i * 150),
                        ),
                    label = "hop",
                )

            Box(
                modifier =
                    Modifier.size(dotSize)
                        .graphicsLayer { translationY = dy }
                        .background(color = dotColor, shape = CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHoppingDotsLoaderPrimary() {
    AIReadingHelperTheme {
        HoppingDotsLoader(
            dotColor = MaterialTheme.colorScheme.primary,
            dotSize = 12.dp,
            hopHeight = 6.dp,
            modifier = Modifier.padding(16.dp),
        )
    }
}
