package com.freesculpt.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freesculpt.ai.LocalAiAgent
import com.freesculpt.engine.animation.TimelineController
import com.freesculpt.engine.interaction.BrushInteractionController
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

    var animationTime by mutableStateOf(0f)
        private set

    var isAnimationPlaying by mutableStateOf(false)
        private set

    private val aiAgent = LocalAiAgent()
    private val brushController = BrushInteractionController()
    private val timelineController = TimelineController()

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

    fun applySculpt(x: Float, y: Float) {
        viewModelScope.launch {
            brushController.processTouchDrag(
                mesh = scene.mesh,
                x = x,
                y = y,
                radius = scene.brush.radius,
                strength = scene.brush.strength,
                brushType = scene.currentTool.ordinal,
            )
        }
    }

    fun requestAi(prompt: String) {
        viewModelScope.launch {
            aiResponse = aiAgent.generateAdvice(prompt)
        }
    }

    fun playAnimation() {
        isAnimationPlaying = true
        timelineController.play()
    }

    fun pauseAnimation() {
        isAnimationPlaying = false
        timelineController.pause()
    }

    fun stopAnimation() {
        isAnimationPlaying = false
        timelineController.stop()
        animationTime = 0f
    }

    fun setAnimationTime(time: Float) {
        animationTime = time
        timelineController.setTime(time)
    }

    fun getAnimationKeyframes() = timelineController.getKeyframes()
}
