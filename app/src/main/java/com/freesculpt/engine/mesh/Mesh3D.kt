package com.freesculpt.engine.mesh

import kotlin.math.abs

class Mesh3D(
    val vertices: MutableList<Vertex> = mutableListOf(),
    val faces: MutableList<Face> = mutableListOf()
) {
    fun applyBrush(
        center: Vec3,
        radius: Float,
        strength: Float,
        type: BrushType
    ) {
        for (vertex in vertices) {
            val distance = vertex.position.distanceTo(center)
            if (distance <= radius) {
                val influence = 1f - (distance / radius)
                val delta = strength * influence

                when (type) {
                    BrushType.CLAY -> {
                        val direction = (vertex.position - center).normalized()
                        vertex.position = vertex.position + direction * delta
                    }

                    BrushType.SMOOTH -> {
                        val direction = (center - vertex.position).normalized()
                        vertex.position = vertex.position + direction * (delta * 0.35f)
                    }

                    BrushType.GRAB -> {
                        val target = center + (vertex.position - center) * 0.6f
                        vertex.position = vertex.position + (target - vertex.position) * delta
                    }

                    BrushType.FLATTEN -> {
                        val normalized = (vertex.position - center).normalized()
                        vertex.position = center + normalized * (radius * 0.4f) * influence
                    }
                }

                val n = (vertex.position - center).normalized()
                vertex.normal = n
            }
        }
    }

    fun smoothNormals() {
        vertices.forEach { vertex ->
            val target = Vec3(0f, 1f, 0f)
            val diff = target - vertex.normal
            vertex.normal = vertex.normal + (diff * 0.1f)
        }
    }

    fun boundingBox(): Pair<Vec3, Vec3> {
        if (vertices.isEmpty()) return Vec3(0f, 0f, 0f) to Vec3(0f, 0f, 0f)

        var min = vertices.first().position
        var max = vertices.first().position

        vertices.forEach { vertex ->
            min = Vec3(
                min.x.coerceAtMost(vertex.position.x),
                min.y.coerceAtMost(vertex.position.y),
                min.z.coerceAtMost(vertex.position.z)
            )
            max = Vec3(
                max.x.coerceAtLeast(vertex.position.x),
                max.y.coerceAtLeast(vertex.position.y),
                max.z.coerceAtLeast(vertex.position.z)
            )
        }

        return min to max
    }
}

enum class BrushType {
    CLAY,
    SMOOTH,
    GRAB,
    FLATTEN
}
