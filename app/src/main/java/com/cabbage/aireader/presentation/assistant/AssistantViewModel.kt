package com.cabbage.aireader.presentation.assistant

import androidx.lifecycle.ViewModel
import com.cabbage.aireader.presentation.model.Bubble
import com.cabbage.aireader.presentation.model.HighlightedTextBubble
import com.cabbage.aireader.presentation.model.LoadingBubble
import com.cabbage.aireader.presentation.model.TranslationBubble
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AssistantViewModel @Inject constructor() : ViewModel() {

    private val _bubbles = MutableStateFlow<List<Bubble>>(fakeBubbles())
    val bubbles: StateFlow<List<Bubble>> = _bubbles.asStateFlow()
}

private fun fakeBubbles(): List<Bubble> =
    listOf(
        HighlightedTextBubble("She had a period of temporary amnesia."),
        LoadingBubble,
        TranslationBubble("她经历了一段短暂的失忆。"),
        HighlightedTextBubble(
            "And Solcom was not certain that Frost was the product originally desired. " +
                "The project had undergone so many revisions that the original specifications were largely forgotten. " +
                "What remained was a series of compromises and patches, each one layering uncertainty onto the design."
        ),
        TranslationBubble(
            "索尔科姆也不确定弗罗斯特是否是最初想要的产品。该项目经历了太多修订，最初的规格大多已被遗忘。 " + "剩下的是一系列的妥协和修补，每一个都在设计上叠加了不确定性。"
        ),
        HighlightedTextBubble("The machine was silent for a long moment."),
        TranslationBubble("机器沉默了好一会儿。"),
        HighlightedTextBubble(
            "When the response finally came, it was not in any language she could recognize. " +
                "The symbols flickered across the display in patterns that seemed to shift and reform even as she looked at them. " +
                "It might have been mathematics. It might have been music. Or something that had not yet been given a name."
        ),
        TranslationBubble(
            "当回应终于到来时，并不是她所能识别的任何语言。符号在显示屏上闪烁，呈现出似乎在她注视下不断变化和重组的模式。 " +
                "那可能是数学。可能是音乐。或者是某种尚未被命名的事物。"
        ),
        HighlightedTextBubble("Frost considered the question."),
        LoadingBubble,
        TranslationBubble("弗罗斯特思考着这个问题。"),
        HighlightedTextBubble(
            "The room was designed to minimize external stimulus—white walls, constant temperature, no windows. " +
                "Yet even here, in this controlled environment, she could feel the weight of the world outside. " +
                "Every query was a thread connecting her to something vast and uncertain. " +
                "She did not know if that was a flaw in her design or the design of the room."
        ),
        TranslationBubble(
            "这个房间的设计旨在将外部刺激降到最低——白墙、恒温、无窗。然而即使在这里，在这个受控的环境中，她仍能感受到外面世界的重量。 " +
                "每一个问题都是一根线，将她与某种广阔而不确定的东西连接起来。她不知道这是她设计中的缺陷，还是房间设计的缺陷。"
        ),
        HighlightedTextBubble("Short one."),
        TranslationBubble("短句。"),
    )
