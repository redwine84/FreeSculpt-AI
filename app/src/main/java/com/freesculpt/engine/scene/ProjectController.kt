package com.freesculpt.engine.scene

import com.freesculpt.engine.mesh.BrushType
import com.freesculpt.engine.mesh.Mesh3D
import com.freesculpt.engine.mesh.createSphereMesh
import com.freesculpt.engine.mesh.Vec3

enum class EditorMode {
    SCULPT,
    RIG,
    ANIMATE,
    AI,
    EXPORT
}

enum class SculptTool {
    CLAY,
    SMOOTH,
    GRAB,
    FLATTEN,
    RIG,
    AI
}

data class SceneStats(
    val vertexCount: Int,
    val triangleCount: Int,
    val animationReady: Boolean,
    val aiReady: Boolean,
)

data class BrushPreset(
    val radius: Float = 0.55f,
    val strength: Float = 0.24f,
    val symmetry: Boolean = true,
    val pressure: Float = 1f,
)

data class ProjectScene(
    val name: String = "Untitled Sculpt",
    val mesh: Mesh3D = createSphereMesh(1f, 28),
    val currentTool: SculptTool = SculptTool.CLAY,
    val brush: BrushPreset = BrushPreset(),
    val mode: EditorMode = EditorMode.SCULPT,
) {
    fun stats(): SceneStats {
        return SceneStats(
            vertexCount = mesh.vertices.size,
            triangleCount = mesh.faces.size,
            animationReady = true,
            aiReady = true,
        )
    }

    fun toBrushType(): BrushType = when (currentTool) {
        SculptTool.CLAY -> BrushType.CLAY
        SculptTool.SMOOTH -> BrushType.SMOOTH
        SculptTool.GRAB -> BrushType.GRAB
        SculptTool.FLATTEN -> BrushType.FLATTEN
        SculptTool.RIG -> BrushType.CLAY
        SculptTool.AI -> BrushType.CLAY
    }

    fun updateBrush(radius: Float, strength: Float) = copy(
        brush = brush.copy(radius = radius, strength = strength)
    )

    fun setMode(mode: EditorMode) = copy(mode = mode)
}

class ProjectController(
    initialScene: ProjectScene = ProjectScene(),
) {
    var currentScene: ProjectScene = initialScene
        private set

    fun setTool(tool: SculptTool) {
        currentScene = currentScene.copy(currentTool = tool)
    }

    fun setMode(mode: EditorMode) {
        currentScene = currentScene.copy(mode = mode)
    }

    fun setBrush(radius: Float, strength: Float) {
        currentScene = currentScene.updateBrush(radius, strength)
    }

    fun applySculpt(center: Vec3) {
        currentScene.mesh.applyBrush(
            center = center,
            radius = currentScene.brush.radius,
            strength = currentScene.brush.strength,
            type = currentScene.toBrushType(),
        )
    }
}
