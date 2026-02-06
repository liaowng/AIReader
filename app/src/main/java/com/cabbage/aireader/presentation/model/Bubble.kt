package com.cabbage.aireader.presentation.model

enum class BubbleOwner {
    User,
    Assistant,
}

/** Base type for chat bubble content. */
sealed interface Bubble {
    val text: String
    val owner: BubbleOwner
}

/** Bubble showing highlighted text (e.g. from the reader). */
data class HighlightedTextBubble(override val text: String) : Bubble {
    override val owner: BubbleOwner = BubbleOwner.User
}

/** Bubble showing a translation. */
data class TranslationBubble(override val text: String) : Bubble {
    override val owner: BubbleOwner = BubbleOwner.Assistant
}

/** Placeholder bubble on the AI side while waiting for a response. */
object LoadingBubble : Bubble {
    override val text: String
        get() = ""

    override val owner: BubbleOwner = BubbleOwner.Assistant
}
