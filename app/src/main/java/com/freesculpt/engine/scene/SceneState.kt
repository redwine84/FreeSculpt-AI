package com.freesculpt.engine.scene

import com.freesculpt.engine.mesh.BrushType
import com.freesculpt.engine.mesh.Mesh3D
import com.freesculpt.engine.mesh.createSphereMesh

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

data class ProjectScene(
    val name: String = "Untitled Sculpt",
    val mesh: Mesh3D = createSphereMesh(1f, 32),
    val currentTool: SculptTool = SculptTool.CLAY,
    val brushRadius: Float = 0.55f,
    val brushStrength: Float = 0.25f,
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
}
