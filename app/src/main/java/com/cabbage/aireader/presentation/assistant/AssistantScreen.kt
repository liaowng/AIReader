package com.cabbage.aireader.presentation.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cabbage.aireader.presentation.assistant.components.ChatBubble
import com.cabbage.aireader.presentation.assistant.components.LoadingChatBubble
import com.cabbage.aireader.presentation.model.Bubble
import com.cabbage.aireader.presentation.model.BubbleOwner
import com.cabbage.aireader.presentation.model.HighlightedTextBubble
import com.cabbage.aireader.presentation.model.LoadingBubble
import com.cabbage.aireader.presentation.model.TranslationBubble

@Composable
fun AssistantScreen(
    modifier: Modifier = Modifier,
    viewModel: AssistantViewModel = hiltViewModel(),
) {
    val bubbles by viewModel.bubbles.collectAsState()

    AssistantScreenContent(bubbles = bubbles, modifier = modifier)
}

@Composable
fun AssistantScreenContent(bubbles: List<Bubble>, modifier: Modifier = Modifier) {

    Column(modifier = modifier.padding(16.dp).background(color = Color.Transparent)) {
        Text(
            text = "Assistant",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            itemsIndexed(items = bubbles, key = { index, _ -> index }) { _, bubble ->
                BubbleContainer(bubble) {
                    when (it) {
                        is LoadingBubble ->
                            LoadingChatBubble(
                                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                            )
                        is HighlightedTextBubble,
                        is TranslationBubble ->
                            ChatBubble(
                                content = bubble,
                                peekLineCount = 3,
                                backgroundColor =
                                    when (it.owner) {
                                        BubbleOwner.User ->
                                            MaterialTheme.colorScheme.primaryContainer
                                        BubbleOwner.Assistant ->
                                            MaterialTheme.colorScheme.tertiaryContainer
                                    },
                                modifier =
                                    Modifier.fillMaxWidth(0.7f)
                                        .wrapContentWidth(
                                            align =
                                                when (it.owner) {
                                                    BubbleOwner.User -> Alignment.End
                                                    BubbleOwner.Assistant -> Alignment.Start
                                                }
                                        ),
                            )
                    }
                }
            }
        }
    }
}

@Composable
private fun BubbleContainer(bubble: Bubble, content: @Composable (Bubble) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            when (bubble.owner) {
                BubbleOwner.User -> Arrangement.End
                BubbleOwner.Assistant -> Arrangement.Start
            },
        verticalAlignment = Alignment.Top,
    ) {
        content(bubble)
    }
}
