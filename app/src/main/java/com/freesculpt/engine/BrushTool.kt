package com.freesculpt.engine.mesh

import kotlin.math.abs
import kotlin.math.max

class BrushTool {
    fun sculpt(
        mesh: Mesh3D,
        center: Vec3,
        radius: Float,
        strength: Float,
        type: BrushType,
    ) {
        mesh.applyBrush(center, radius, strength, type)
    }

    fun smooth(mesh: Mesh3D, center: Vec3, radius: Float, strength: Float) {
        sculpt(mesh, center, radius, strength, BrushType.SMOOTH)
    }

    fun clay(mesh: Mesh3D, center: Vec3, radius: Float, strength: Float) {
        sculpt(mesh, center, radius, strength, BrushType.CLAY)
    }
}
