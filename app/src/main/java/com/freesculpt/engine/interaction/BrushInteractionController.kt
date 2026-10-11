package com.freesculpt.engine.interaction

import com.freesculpt.engine.mesh.Mesh3D
import com.freesculpt.engine.mesh.Vec3
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BrushInteractionController {
    private var isDragging = false
    private var lastX = 0f
    private var lastY = 0f

    suspend fun processTouchDrag(
        mesh: Mesh3D,
        x: Float,
        y: Float,
        radius: Float,
        strength: Float,
        brushType: Int,
    ) = withContext(Dispatchers.Default) {
        val center = Vec3(x, y, 0f)
        mesh.applyBrush(
            center = center,
            radius = radius,
            strength = strength,
            type = when (brushType) {
                0 -> com.freesculpt.engine.mesh.BrushType.CLAY
                1 -> com.freesculpt.engine.mesh.BrushType.SMOOTH
                2 -> com.freesculpt.engine.mesh.BrushType.GRAB
                else -> com.freesculpt.engine.mesh.BrushType.FLATTEN
            },
        )
        mesh.smoothNormals()
    }

    fun setDragging(value: Boolean) {
        isDragging = value
    }

    fun updatePosition(x: Float, y: Float) {
        lastX = x
        lastY = y
    }
}
