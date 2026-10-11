package com.freesculpt.engine.mesh

import java.io.File
import org.json.JSONArray
import org.json.JSONObject

class OBJExporter {
    fun export(mesh: Mesh3D, file: File) {
        val sb = StringBuilder()
        sb.append("# FreeSculpt-AI Mesh Export\n")
        sb.append("# Vertices: ${mesh.vertices.size}\n")
        sb.append("# Faces: ${mesh.faces.size}\n\n")

        mesh.vertices.forEach { vertex ->
            sb.append("v ${vertex.position.x} ${vertex.position.y} ${vertex.position.z}\n")
        }
        sb.append("\n")

        mesh.vertices.forEach { vertex ->
            sb.append("vn ${vertex.normal.x} ${vertex.normal.y} ${vertex.normal.z}\n")
        }
        sb.append("\n")

        mesh.faces.forEach { face ->
            val indices = face.indices.map { it + 1 }.joinToString(" ")
            sb.append("f $indices\n")
        }

        file.writeText(sb.toString())
    }
}

class OBJImporter {
    fun import(file: File): Mesh3D? {
        try {
            val lines = file.readLines()
            val vertices = mutableListOf<Vertex>()
            val faces = mutableListOf<Face>()

            lines.forEach { line ->
                when {
                    line.startsWith("v ") -> {
                        val parts = line.substring(2).split("\\s+".toRegex())
                        if (parts.size >= 3) {
                            vertices.add(Vertex(
                                vertices.size,
                                Vec3(parts[0].toFloat(), parts[1].toFloat(), parts[2].toFloat())
                            ))
                        }
                    }
                    line.startsWith("f ") -> {
                        val parts = line.substring(2).split("\\s+".toRegex())
                        val indices = parts.map { (it.split("/")[0].toInt() - 1) }.toIntArray()
                        if (indices.size >= 3) {
                            faces.add(Face(indices))
                        }
                    }
                }
            }

            return if (vertices.isNotEmpty() && faces.isNotEmpty()) {
                Mesh3D(vertices, faces)
            } else null
        } catch (e: Exception) {
            return null
        }
    }
}

class GLTFExporter {
    fun export(mesh: Mesh3D, file: File) {
        val json = JSONObject().apply {
            put("asset", JSONObject().apply {
                put("version", "2.0")
                put("generator", "FreeSculpt-AI")
            })
            put("scene", 0)
            put("scenes", JSONArray().apply {
                put(JSONObject().apply {
                    put("nodes", JSONArray().apply {
                        put(0)
                    })
                })
            })
            put("nodes", JSONArray().apply {
                put(JSONObject().apply {
                    put("mesh", 0)
                    put("name", "Mesh")
                })
            })
            put("meshes", JSONArray().apply {
                put(JSONObject().apply {
                    put("name", "Mesh")
                    put("primitives", JSONArray().apply {
                        put(JSONObject().apply {
                            put("mode", 4)
                            put("attributes", JSONObject().apply {
                                put("POSITION", 0)
                            })
                        })
                    })
                })
            })
            put("accessors", JSONArray().apply {
                put(JSONObject().apply {
                    put("bufferView", 0)
                    put("componentType", 5126)
                    put("count", mesh.vertices.size)
                    put("type", "VEC3")
                })
            })
        }
        file.writeText(json.toString(2))
    }
}
