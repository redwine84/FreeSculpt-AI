package com.freesculpt.engine.animation

import com.freesculpt.engine.mesh.Vec3

class TimelineController {
    private val clips = mutableListOf<AnimationClip>()
    private var currentTime = 0f
    private var isPlaying = false

    fun addClip(clip: AnimationClip) {
        clips.add(clip)
    }

    fun play() {
        isPlaying = true
    }

    fun pause() {
        isPlaying = false
    }

    fun stop() {
        isPlaying = false
        currentTime = 0f
    }

    fun setTime(time: Float) {
        currentTime = time.coerceIn(0f, (clips.firstOrNull()?.duration ?: 1f))
    }

    fun update(deltaTime: Float) {
        if (isPlaying && clips.isNotEmpty()) {
            currentTime += deltaTime
            val clipDuration = clips.first().duration
            if (currentTime >= clipDuration) {
                currentTime = 0f
            }
        }
    }

    fun getKeyframes(): List<Keyframe> {
        return clips.firstOrNull()?.keyframes ?: emptyList()
    }

    fun getCurrentPose(): Map<String, Vec3> {
        val clip = clips.firstOrNull() ?: return emptyMap()
        if (clip.keyframes.isEmpty()) return emptyMap()

        val keyframes = clip.keyframes.sortedBy { it.time }
        val current = keyframes.findLast { it.time <= currentTime }
        val next = keyframes.find { it.time > currentTime }

        return if (current != null && next != null) {
            val t = (currentTime - current.time) / (next.time - current.time)
            interpolatePose(current.pose, next.pose, t)
        } else {
            current?.pose ?: emptyMap()
        }
    }

    private fun interpolatePose(pose1: Map<String, Vec3>, pose2: Map<String, Vec3>, t: Float): Map<String, Vec3> {
        val result = mutableMapOf<String, Vec3>()
        pose1.forEach { (key, vec) ->
            val target = pose2[key] ?: vec
            result[key] = Vec3(
                vec.x + (target.x - vec.x) * t,
                vec.y + (target.y - vec.y) * t,
                vec.z + (target.z - vec.z) * t,
            )
        }
        return result
    }
}
