package com.freesculpt.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ExportPanel(
    modifier: Modifier = Modifier,
    onExportOBJ: () -> Unit = {},
    onExportGLTF: () -> Unit = {},
    onExportJSON: () -> Unit = {},
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
                text = "Export Model",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Button(
                onClick = onExportOBJ,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Export as OBJ")
            }

            Button(
                onClick = onExportGLTF,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Export as glTF 2.0")
            }

            Button(
                onClick = onExportJSON,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Export as JSON")
            }
        }
    }
}
