package com.cabbage.aireader.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CompositeUiState())
    val uiState: StateFlow<CompositeUiState> = _uiState.asStateFlow()

    fun setLayoutMode(mode: LayoutMode) {
        _uiState.update { it.copy(layoutMode = mode) }
    }

    fun toggleAssistantPanel() {
        _uiState.update { it.copy(isAssistantPanelVisible = !it.isAssistantPanelVisible) }
    }
}

data class CompositeUiState(
    val layoutMode: LayoutMode = LayoutMode.SIDE_BY_SIDE,
    val isAssistantPanelVisible: Boolean = false,
)

enum class LayoutMode {
    SIDE_BY_SIDE, // Tablet landscape - split screen
    BOTTOM_SHEET, // Mobile/portrait - bottom sheet
}
