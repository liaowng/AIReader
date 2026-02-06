package com.cabbage.aireader.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cabbage.aireader.presentation.assistant.AssistantScreen
import com.cabbage.aireader.presentation.reading.ReadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current

    // Determine layout mode based on screen size and orientation
    val layoutMode =
        remember(configuration) {
            val screenWidthDp = configuration.screenWidthDp
            val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

            when {
                screenWidthDp >= 600 && isLandscape -> LayoutMode.SIDE_BY_SIDE
                else -> LayoutMode.BOTTOM_SHEET
            }
        }

    LaunchedEffect(layoutMode) { viewModel.setLayoutMode(layoutMode) }

    when (uiState.layoutMode) {
        LayoutMode.SIDE_BY_SIDE -> {
            SideBySideLayout()
        }
        LayoutMode.BOTTOM_SHEET -> {
            BottomSheetLayout(
                isSheetVisible = uiState.isAssistantPanelVisible,
                onToggleSheet = { viewModel.toggleAssistantPanel() },
            )
        }
    }
}

@Composable
private fun SideBySideLayout() {
    Row(modifier = Modifier.fillMaxSize()) {
        // Reading view on the left
        Surface(modifier = Modifier.weight(1f).fillMaxHeight(), tonalElevation = 1.dp) {
            ReadingScreen(onFabClick = {})
        }

        VerticalDivider()

        // Assistant view on the right
        Surface(modifier = Modifier.weight(1f).fillMaxHeight(), tonalElevation = 2.dp) {
            AssistantScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetLayout(isSheetVisible: Boolean, onToggleSheet: () -> Unit) {
    // 1. Skip the 'Partially Expanded' (peek) state entirely
    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { newState ->
                // Logic to ensure it only stays hidden or expanded
                newState != SheetValue.PartiallyExpanded
            },
        )

    // Main reading view
    ReadingScreen(modifier = Modifier.fillMaxSize(), onFabClick = onToggleSheet)

    // Bottom sheet for assistant — fixed 80% height, no peek; extends to bottom edge (no gap)
    if (isSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onToggleSheet,
            sheetState = sheetState,
            dragHandle = null,
        ) {
            AssistantScreen(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f))
        }
    }
}
