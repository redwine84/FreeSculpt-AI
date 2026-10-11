package com.freesculpt.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freesculpt.ai.LocalAiAgent
import kotlinx.coroutines.launch

class AiAssistantViewModel : ViewModel() {
    var prompt by mutableStateOf("Suggest a balanced bust silhouette with clean topology for mobile sculpting.")
        private set

    var response by mutableStateOf("Local AI is ready to help with sculpt ideas and mesh refinement.")
        private set

    private val aiAgent = LocalAiAgent()

    fun setPrompt(value: String) {
        prompt = value
    }

    fun generate() {
        viewModelScope.launch {
            response = aiAgent.generateAdvice(prompt)
        }
    }
}
