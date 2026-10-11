package com.freesculpt.engine.viewport

import android.opengl.GLES20
import com.freesculpt.engine.mesh.Mesh3D
import com.freesculpt.engine.mesh.Vec3
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class OpenGLRenderer {
    private var vertexBuffer: FloatBuffer? = null
    private var normalBuffer: FloatBuffer? = null
    private var indexBuffer: FloatBuffer? = null
    private var programHandle: Int = 0
    private var vertexCount: Int = 0

    fun initShaders() {
        val vertexShader = """
            uniform mat4 uMatrix;
            attribute vec4 vPosition;
            attribute vec3 vNormal;
            varying vec3 fragNormal;
            void main() {
                gl_Position = uMatrix * vPosition;
                fragNormal = normalize(vNormal);
            }
        """.trimIndent()

        val fragmentShader = """
            precision mediump float;
            varying vec3 fragNormal;
            uniform vec3 uLight;
            void main() {
                vec3 light = normalize(uLight);
                float diff = max(dot(fragNormal, light), 0.2);
                gl_FragColor = vec4(0.8 * diff, 0.6 * diff, 1.0 * diff, 1.0);
            }
        """.trimIndent()

        val vs = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER)
        GLES20.glShaderSource(vs, vertexShader)
        GLES20.glCompileShader(vs)

        val fs = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER)
        GLES20.glShaderSource(fs, fragmentShader)
        GLES20.glCompileShader(fs)

        programHandle = GLES20.glCreateProgram()
        GLES20.glAttachShader(programHandle, vs)
        GLES20.glAttachShader(programHandle, fs)
        GLES20.glLinkProgram(programHandle)
    }

    fun loadMesh(mesh: Mesh3D) {
        val positions = mutableListOf<Float>()
        val normals = mutableListOf<Float>()
        val indices = mutableListOf<Int>()

        mesh.vertices.forEach { v ->
            positions.addAll(listOf(v.position.x, v.position.y, v.position.z))
            normals.addAll(listOf(v.normal.x, v.normal.y, v.normal.z))
        }

        mesh.faces.forEach { face ->
            indices.addAll(face.indices.toList())
        }

        vertexCount = indices.size

        vertexBuffer = ByteBuffer.allocateDirect(positions.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexBuffer?.put(positions.toFloatArray())
        vertexBuffer?.position(0)

        normalBuffer = ByteBuffer.allocateDirect(normals.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
        normalBuffer?.put(normals.toFloatArray())
        normalBuffer?.position(0)
    }

    fun render() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        GLES20.glUseProgram(programHandle)

        vertexBuffer?.let {
            val posHandle = GLES20.glGetAttribLocation(programHandle, "vPosition")
            GLES20.glEnableVertexAttribArray(posHandle)
            GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false, 0, it)
        }

        normalBuffer?.let {
            val normHandle = GLES20.glGetAttribLocation(programHandle, "vNormal")
            GLES20.glEnableVertexAttribArray(normHandle)
            GLES20.glVertexAttribPointer(normHandle, 3, GLES20.GL_FLOAT, false, 0, it)
        }

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
    }
}
