package com.cabbage.aireader.presentation.assistant.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cabbage.aireader.presentation.model.Bubble
import com.cabbage.aireader.presentation.model.HighlightedTextBubble
import com.cabbage.aireader.presentation.model.TranslationBubble
import com.cabbage.aireader.theme.AIReadingHelperTheme

private val BubbleCornerRadius = 16.dp
private val BubblePaddingHorizontal = 12.dp
private val BubblePaddingVertical = 8.dp

@Composable
fun ChatBubble(
    content: Bubble,
    peekLineCount: Int,
    backgroundColor: Color,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier,
    initialExpanded: Boolean = false,
) {
    var expanded by remember { mutableStateOf(initialExpanded) }
    val shape = RoundedCornerShape(BubbleCornerRadius)

    Box(
        modifier =
            modifier
                .animateContentSize(animationSpec = tween(durationMillis = 250))
                .clip(shape)
                .background(backgroundColor)
                .clickable(enabled = true, onClick = { expanded = !expanded })
                .padding(horizontal = BubblePaddingHorizontal, vertical = BubblePaddingVertical)
    ) {
        Text(
            text = content.text,
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor,
            maxLines = if (expanded) Int.MAX_VALUE else peekLineCount,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun LoadingChatBubble(backgroundColor: Color, contentColor: Color, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(BubbleCornerRadius)
    val bodyMedium = MaterialTheme.typography.bodyMedium
    val lineHeightDp = with(LocalDensity.current) { bodyMedium.lineHeight.toDp() }

    Box(
        modifier =
            modifier
                .wrapContentWidth()
                .clip(shape)
                .background(backgroundColor)
                .padding(horizontal = BubblePaddingHorizontal, vertical = BubblePaddingVertical)
    ) {
        Box(modifier = Modifier.height(lineHeightDp), contentAlignment = Alignment.Center) {
            HoppingDotsLoader(dotColor = contentColor, dotSize = 8.dp, hopHeight = 4.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoadingChatBubble() {
    AIReadingHelperTheme {
        LoadingChatBubble(
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewChatBubble() {
    AIReadingHelperTheme {
        ChatBubble(
            content = HighlightedTextBubble("This is a short highlighted excerpt."),
            peekLineCount = 3,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewChatBubbleTranslationExpanded() {
    AIReadingHelperTheme {
        ChatBubble(
            content =
                TranslationBubble(
                    "This is a longer translation that should be clamped to a few lines when collapsed. " +
                        "When expanded, the full text is visible without ellipsis."
                ),
            peekLineCount = 2,
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            modifier = Modifier.padding(16.dp),
            initialExpanded = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewChatBubbleColumn() {
    AIReadingHelperTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ChatBubble(
                content = HighlightedTextBubble("First bubble. Short text."),
                peekLineCount = 3,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            )
            ChatBubble(
                content =
                    TranslationBubble(
                        "This is the middle bubble with a lot of text so that it overflows when collapsed. " +
                            "We use a small peek line count so the ellipsis appears at the end. " +
                            "The rest of the content is hidden until the user expands it."
                    ),
                peekLineCount = 2,
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            )
            ChatBubble(
                content = HighlightedTextBubble("Third bubble. Also short."),
                peekLineCount = 3,
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            )
            LoadingChatBubble(
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
            )
        }
    }
}
