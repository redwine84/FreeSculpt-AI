package com.freesculpt.io

import java.io.File
import org.json.JSONObject

class ModelExporter {
    fun exportJson(file: File, name: String, vertices: Int, faces: Int) {
        val payload = JSONObject().apply {
            put("name", name)
            put("vertices", vertices)
            put("faces", faces)
            put("format", "freesculpt-prototype")
        }
        file.writeText(payload.toString(2))
    }

    fun exportSummary(name: String, vertices: Int, faces: Int): String {
        return "Model '$name' exported: $vertices vertices, $faces faces."
    }
}
