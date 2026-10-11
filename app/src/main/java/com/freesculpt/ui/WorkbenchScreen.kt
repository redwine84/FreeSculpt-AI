package com.freesculpt.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freesculpt.engine.mesh.Vec3
import com.freesculpt.engine.scene.EditorMode
import com.freesculpt.engine.scene.SculptTool
import com.freesculpt.viewmodel.ProjectViewModel

@Composable
fun WorkbenchScreen(
    onBackHome: () -> Unit,
    viewModel: ProjectViewModel = viewModel(),
) {
    val scene = viewModel.scene
    var selectedTool by remember { mutableStateOf(SculptTool.CLAY) }
    val stats = remember(scene) { scene.stats() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Workbench",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onBackHome) {
                    Text("Home")
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                ) {
                    Text("Save")
                }
            }
        }

        ModeTabs(
            selected = scene.mode.name,
            onSelect = { modeName ->
                val mode = when (modeName) {
                    "Rig" -> EditorMode.RIG
                    "Animate" -> EditorMode.ANIMATE
                    "AI" -> EditorMode.AI
                    "Export" -> EditorMode.EXPORT
                    else -> EditorMode.SCULPT
                }
                viewModel.setMode(mode)
            },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Card(
                modifier = Modifier.width(220.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    ToolChip("Clay", selectedTool == SculptTool.CLAY) {
                        selectedTool = SculptTool.CLAY
                        viewModel.setTool(SculptTool.CLAY)
                    }
                    ToolChip("Smooth", selectedTool == SculptTool.SMOOTH) {
                        selectedTool = SculptTool.SMOOTH
                        viewModel.setTool(SculptTool.SMOOTH)
                    }
                    ToolChip("Grab", selectedTool == SculptTool.GRAB) {
                        selectedTool = SculptTool.GRAB
                        viewModel.setTool(SculptTool.GRAB)
                    }
                    ToolChip("Flatten", selectedTool == SculptTool.FLATTEN) {
                        selectedTool = SculptTool.FLATTEN
                        viewModel.setTool(SculptTool.FLATTEN)
                    }
                    ToolChip("Rig", selectedTool == SculptTool.RIG) {
                        selectedTool = SculptTool.RIG
                        viewModel.setTool(SculptTool.RIG)
                    }
                    ToolChip("AI assist", selectedTool == SculptTool.AI) {
                        selectedTool = SculptTool.AI
                        viewModel.setTool(SculptTool.AI)
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            ) {
                SculptViewport(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    onBrushAt = { x, y ->
                        viewModel.applySculpt(x, y)
                    },
                )
            }

            Card(
                modifier = Modifier.width(240.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        text = "Scene summary",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    SceneStat("Vertices", stats.vertexCount.toString())
                    SceneStat("Polys", stats.triangleCount.toString())
                    SceneStat("Animation", if (stats.animationReady) "Ready" else "Pending")
                    SceneStat("AI", if (stats.aiReady) "Online" else "Offline")
                    SceneStat("Tool", selectedTool.name)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ProjectOverviewCard(
                title = "Workspace",
                subtitle = scene.name,
                modifier = Modifier.weight(1f),
            )
            ProjectOverviewCard(
                title = "Mode",
                subtitle = scene.mode.name,
                modifier = Modifier.weight(1f),
            )
        }

        when (scene.mode) {
            EditorMode.ANIMATE -> AnimationTimelinePanel(modifier = Modifier.fillMaxWidth())
            EditorMode.EXPORT -> ExportPanel(
                modifier = Modifier.fillMaxWidth(),
                onExportOBJ = { /* export OBJ */ },
                onExportGLTF = { /* export glTF */ },
            )
            EditorMode.AI -> AIAssistantPanel(modifier = Modifier.fillMaxWidth())
            else -> ImportPanel(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun ToolChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
            contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        ),
    ) {
        Text(
            text = label,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
        )
    }
}

@Composable
private fun SceneStat(title: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}
