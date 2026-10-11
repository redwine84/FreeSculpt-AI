package com.freesculpt.engine.mesh

import kotlin.math.abs
import kotlin.math.sqrt

data class Vec3(
    var x: Float,
    var y: Float,
    var z: Float
) {
    fun length(): Float = sqrt((x * x) + (y * y) + (z * z))

    fun normalized(): Vec3 {
        val len = length()
        return if (len <= 0.0001f) Vec3(0f, 0f, 0f) else Vec3(x / len, y / len, z / len)
    }

    fun distanceTo(other: Vec3): Float = sqrt(
        ((x - other.x) * (x - other.x)) +
            ((y - other.y) * (y - other.y)) +
            ((z - other.z) * (z - other.z))
    )

    fun addScaled(other: Vec3, scale: Float): Vec3 = Vec3(
        x + (other.x * scale),
        y + (other.y * scale),
        z + (other.z * scale)
    )

    operator fun plus(other: Vec3): Vec3 = Vec3(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vec3): Vec3 = Vec3(x - other.x, y - other.y, z - other.z)
    operator fun times(scale: Float): Vec3 = Vec3(x * scale, y * scale, z * scale)
    operator fun div(scale: Float): Vec3 = Vec3(x / scale, y / scale, z / scale)

    fun clamp(value: Float): Vec3 = Vec3(
        x.coerceIn(-value, value),
        y.coerceIn(-value, value),
        z.coerceIn(-value, value)
    )
}

data class Vertex(
    val id: Int,
    var position: Vec3,
    var normal: Vec3 = Vec3(0f, 1f, 0f)
)

data class Face(
    val indices: IntArray
)

fun createSphereMesh(radius: Float = 1f, segments: Int = 24): Mesh3D {
    val vertices = mutableListOf<Vertex>()
    val faces = mutableListOf<Face>()
    val rings = segments

    for (ring in 0..rings) {
        val v = ring / rings.toFloat()
        val phi = v * kotlin.math.PI.toFloat()
        val sinPhi = kotlin.math.sin(phi)
        val cosPhi = kotlin.math.cos(phi)

        for (segment in 0 until segments) {
            val u = segment / segments.toFloat()
            val theta = u * (2f * kotlin.math.PI.toFloat())
            val x = radius * sinPhi * kotlin.math.cos(theta)
            val y = radius * cosPhi
            val z = radius * sinPhi * kotlin.math.sin(theta)
            vertices.add(Vertex(vertices.size, Vec3(x, y, z)))
        }
    }

    for (ring in 0 until rings) {
        for (segment in 0 until segments) {
            val a = ring * segments + segment
            val b = ring * segments + ((segment + 1) % segments)
            val c = (ring + 1) * segments + segment
            val d = (ring + 1) * segments + ((segment + 1) % segments)

            if (ring < rings - 1) {
                faces.add(Face(intArrayOf(a, c, b)))
                faces.add(Face(intArrayOf(b, c, d)))
            }
        }
    }

    return Mesh3D(vertices, faces)
}
