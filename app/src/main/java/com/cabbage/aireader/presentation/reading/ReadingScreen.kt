package com.cabbage.aireader.presentation.reading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cabbage.aireader.presentation.model.Paragraph

/** No-op text toolbar to suppress the default selection context menu (Copy, etc.). */
private val NoOpTextToolbar =
    object : TextToolbar {
        override fun showMenu(
            rect: Rect,
            onCopyRequested: (() -> Unit)?,
            onPasteRequested: (() -> Unit)?,
            onCutRequested: (() -> Unit)?,
            onSelectAllRequested: (() -> Unit)?,
        ) {}

        override fun hide() {}

        override val status: TextToolbarStatus
            get() = TextToolbarStatus.Hidden
    }

@Composable
fun ReadingScreen(
    modifier: Modifier = Modifier,
    onFabClick: () -> Unit = {},
    viewModel: ReadingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            if (uiState.hasSelection) {
                FloatingActionButton(onClick = onFabClick) {
                    Icon(Icons.Default.Create, contentDescription = "Ask AI about selection")
                }
            }
        },
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text(
                text = "Reading View",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Card(modifier = Modifier.fillMaxSize()) {
                    CompositionLocalProvider(LocalTextToolbar provides NoOpTextToolbar) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            items(items = uiState.paragraphs, key = { it.id }) { paragraph ->
                                ParagraphTextField(
                                    paragraph = paragraph,
                                    onSelectionChange = { viewModel.updateSelectedText(it) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * One read-only TextField per paragraph for LazyColumn performance; selection is reported via
 * callback.
 */
@Composable
private fun ParagraphTextField(paragraph: Paragraph, onSelectionChange: (String) -> Unit) {
    var textFieldValue by
        remember(paragraph.id) { mutableStateOf(TextFieldValue(paragraph.content)) }
    LaunchedEffect(paragraph.content) {
        if (textFieldValue.text != paragraph.content) {
            textFieldValue = TextFieldValue(paragraph.content)
        }
    }
    TextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            textFieldValue = newValue
            val sel = newValue.selection
            val selected = if (sel.collapsed) "" else newValue.text.substring(sel.min, sel.max)
            onSelectionChange(selected)
        },
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge,
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
    )
}
