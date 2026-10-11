package com.freesculpt.engine.animation

import com.freesculpt.engine.mesh.Vec3

data class Bone(
    val name: String,
    val parent: String? = null,
    var position: Vec3,
    var rotation: Vec3 = Vec3(0f, 0f, 0f),
)

data class Keyframe(
    val time: Float,
    val pose: Map<String, Vec3>,
)

data class AnimationClip(
    val name: String = "Idle",
    val duration: Float = 2.5f,
    val keyframes: MutableList<Keyframe> = mutableListOf(),
)

class RigSystem(
    private val bones: MutableList<Bone> = mutableListOf(),
) {
    fun addBone(name: String, parent: String? = null, position: Vec3) {
        bones.add(Bone(name = name, parent = parent, position = position))
    }

    fun previewPose(): List<Bone> = bones.toList()

    fun sampleTime(time: Float): Map<String, Vec3> {
        return bones.associate { bone ->
            bone.name to (bone.position + Vec3(time, time * 0.5f, time * 0.25f))
        }
    }
}
