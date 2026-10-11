package com.freesculpt.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freesculpt.ai.LocalAiAgent
import com.freesculpt.engine.mesh.Vec3
import com.freesculpt.engine.scene.EditorMode
import com.freesculpt.engine.scene.ProjectScene
import com.freesculpt.engine.scene.SculptTool
import kotlinx.coroutines.launch

class ProjectViewModel : ViewModel() {
    var scene by mutableStateOf(ProjectScene())
        private set

    var aiResponse by mutableStateOf("AI ready to assist with sculpting and mesh refinement.")
        private set

    private val aiAgent = LocalAiAgent()

    fun setTool(tool: SculptTool) {
        scene = scene.copy(currentTool = tool)
    }

    fun setMode(mode: EditorMode) {
        scene = scene.copy(mode = mode)
    }

    fun updateBrush(radius: Float, strength: Float) {
        scene = scene.copy(
            brush = scene.brush.copy(radius = radius, strength = strength)
        )
    }

    fun applySculpt(center: Vec3) {
        scene.mesh.applyBrush(
            center = center,
            radius = scene.brush.radius,
            strength = scene.brush.strength,
            type = scene.toBrushType(),
        )
    }

    fun requestAi(prompt: String) {
        viewModelScope.launch {
            aiResponse = aiAgent.generateAdvice(prompt)
        }
    }
}
