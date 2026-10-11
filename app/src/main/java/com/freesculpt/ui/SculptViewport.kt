package com.freesculpt.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SculptViewport(
    modifier: Modifier = Modifier,
    onBrushAt: (Float, Float) -> Unit = { _, _ -> },
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0B1020))
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val x = ((change.position.x / size.width) * 2f) - 1f
                    val y = ((change.position.y / size.height) * 2f) - 1f
                    onBrushAt(x, y)
                }
            }
    ) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = minOf(size.width, size.height) * 0.28f

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFF1E293B),
                    Color(0xFF0B1020),
                )
            ),
            radius = radius * 1.6f,
            center = Offset(centerX, centerY),
        )

        val path = Path()
        for (i in 0..60) {
            val angle = (i / 60f) * (Math.PI * 2).toFloat()
            val x = centerX + cos(angle.toDouble()).toFloat() * radius * 0.95f
            val y = centerY + sin(angle.toDouble()).toFloat() * radius * 0.95f
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()

        drawPath(
            path = path,
            color = Color(0xFF8B5CF6),
            style = Stroke(width = 2f, cap = StrokeCap.Round),
        )

        for (i in 0..12) {
            val angle = (i / 12f) * (Math.PI.toFloat() * 2f)
            val x1 = centerX + cos(angle.toDouble()).toFloat() * radius
            val y1 = centerY + sin(angle.toDouble()).toFloat() * radius
            val x2 = centerX + cos(angle.toDouble()).toFloat() * radius * 1.24f
            val y2 = centerY + sin(angle.toDouble()).toFloat() * radius * 1.24f
            drawLine(
                color = Color(0xFF22D3EE).copy(alpha = 0.65f),
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = 1.2f,
            )
        }
    }
}
