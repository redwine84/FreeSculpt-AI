package com.freesculpt.engine.mesh

import kotlin.math.cos
import kotlin.math.sin

fun createIcosahedronMesh(radius: Float = 1f): Mesh3D {
    val t = (1f + kotlin.math.sqrt(5f)) / 2f
    val vertices = mutableListOf(
        Vec3(-1f, t, 0f), Vec3(1f, t, 0f),
        Vec3(-1f, -t, 0f), Vec3(1f, -t, 0f),
        Vec3(0f, -1f, t), Vec3(0f, 1f, t),
        Vec3(0f, -1f, -t), Vec3(0f, 1f, -t),
        Vec3(t, 0f, -1f), Vec3(t, 0f, 1f),
        Vec3(-t, 0f, -1f), Vec3(-t, 0f, 1f),
    ).mapIndexed { idx, v ->
        val norm = v.normalized()
        Vertex(idx, Vec3(norm.x * radius, norm.y * radius, norm.z * radius))
    }.toMutableList()

    val faces = mutableListOf(
        Face(intArrayOf(0, 11, 5)), Face(intArrayOf(0, 5, 1)),
        Face(intArrayOf(0, 1, 7)), Face(intArrayOf(0, 7, 10)),
        Face(intArrayOf(0, 10, 11)), Face(intArrayOf(1, 5, 9)),
        Face(intArrayOf(5, 11, 4)), Face(intArrayOf(11, 10, 2)),
        Face(intArrayOf(10, 7, 6)), Face(intArrayOf(7, 1, 8)),
        Face(intArrayOf(3, 9, 4)), Face(intArrayOf(3, 4, 2)),
        Face(intArrayOf(3, 2, 6)), Face(intArrayOf(3, 6, 8)),
        Face(intArrayOf(3, 8, 9)), Face(intArrayOf(4, 9, 5)),
        Face(intArrayOf(2, 4, 11)), Face(intArrayOf(6, 2, 10)),
        Face(intArrayOf(8, 6, 7)), Face(intArrayOf(9, 8, 1)),
    )

    return Mesh3D(vertices, faces)
}
