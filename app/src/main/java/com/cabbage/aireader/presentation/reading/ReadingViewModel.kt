package com.cabbage.aireader.presentation.reading

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabbage.aireader.presentation.model.Paragraph
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ReadingViewModel @Inject constructor(private val application: Application) : ViewModel() {

    private val _uiState = MutableStateFlow(ReadingUiState())
    val uiState: StateFlow<ReadingUiState> = _uiState.asStateFlow()

    init {
        loadTextFromAssets()
    }

    private fun loadTextFromAssets() {
        viewModelScope.launch {
            val paragraphs: List<Paragraph> =
                withContext(Dispatchers.IO) {
                    try {
                        val text =
                            application.assets.open("forbreat").bufferedReader().use {
                                it.readText()
                            }
                        text
                            .split("\n\n")
                            .filter { it.isNotBlank() }
                            .mapIndexed { index, content ->
                                Paragraph(id = index, content = content.trim())
                            }
                    } catch (e: Exception) {
                        listOf(Paragraph(id = 0, content = "Error loading text: ${e.message}"))
                    }
                }
            _uiState.update { it.copy(paragraphs = paragraphs, isLoading = false) }
        }
    }

    fun updateSelectedText(selectedText: String) {
        _uiState.update { it.copy(selectedText = selectedText) }
    }
}

data class ReadingUiState(
    val paragraphs: List<Paragraph> = emptyList(),
    val isLoading: Boolean = true,
    val selectedText: String = "",
) {
    val hasSelection: Boolean
        get() = selectedText.isNotBlank()
}
