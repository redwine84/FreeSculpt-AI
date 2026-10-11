package com.freesculpt.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ImportPanel(
    modifier: Modifier = Modifier,
    onImportOBJ: (String) -> Unit = {},
) {
    var filePath by remember { mutableStateOf("") }

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
                text = "Import Model",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            OutlinedTextField(
                value = filePath,
                onValueChange = { filePath = it },
                label = { Text("File path") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = { onImportOBJ(filePath) },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Import OBJ")
                }
                Button(
                    onClick = { /* import logic */ },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Clear")
                }
            }
        }
    }
}
