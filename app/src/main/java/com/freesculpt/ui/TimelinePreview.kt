package com.freesculpt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TimelinePreview(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "Animation Timeline",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            val frames = listOf("Idle", "Pose A", "Pose B", "Loop")
            frames.forEach { frame ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (frame == "Pose A") MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                            else Color(0xFF1F2937),
                            shape = RoundedCornerShape(10.dp),
                        )
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = frame,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = if (frame == "Pose A") FontWeight.SemiBold else FontWeight.Normal,
                    )
                }
            }
        }
    }
}
