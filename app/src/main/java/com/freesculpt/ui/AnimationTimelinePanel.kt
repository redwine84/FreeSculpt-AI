package com.freesculpt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freesculpt.viewmodel.ProjectViewModel

@Composable
fun AnimationTimelinePanel(
    modifier: Modifier = Modifier,
    viewModel: ProjectViewModel = viewModel(),
) {
    val keyframes = viewModel.getAnimationKeyframes()

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

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = { viewModel.playAnimation() },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Play")
                }
                Button(
                    onClick = { viewModel.pauseAnimation() },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Pause")
                }
                Button(
                    onClick = { viewModel.stopAnimation() },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Stop")
                }
            }

            Slider(
                value = viewModel.animationTime,
                onValueChange = { viewModel.setAnimationTime(it) },
                valueRange = 0f..2.5f,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = "Time: ${String.format("%.2f", viewModel.animationTime)}s",
                style = MaterialTheme.typography.bodySmall,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        color = Color(0xFF1F2937),
                        shape = RoundedCornerShape(8.dp),
                    )
            ) {
                keyframes.forEach { keyframe ->
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (keyframe.time <= viewModel.animationTime) Color(0xFF8B5CF6) else Color(0xFF4B5563),
                                shape = RoundedCornerShape(4.dp),
                            )
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "${keyframe.time}s",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}
