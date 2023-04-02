package com.filimonov.mycrochet.ui.screens.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectDialog(
    show: Boolean,
    onCancel: () -> Unit,
    onConfirm: (name: String, description: String, link: String, crochetSize: Int) -> Unit
) {
    if (show) {
        Dialog(onDismissRequest = onCancel) {
            var name by remember { mutableStateOf("") }
            var description by remember { mutableStateOf("") }
            var link by remember { mutableStateOf("") }
            var crochetSize by remember { mutableStateOf(0) }

            Card(
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                    // headline
                    Text(
                        text = "Add project",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // inputs
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "Name") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        isError = name.isBlank(),
                        supportingText = { if (name.isBlank()) Text(text = "Must not be empty") }
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text(text = "Description") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = false
                    )

                    OutlinedTextField(
                        value = link,
                        onValueChange = { link = it },
                        label = { Text(text = "Video") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = crochetSize.toString(),
                        onValueChange = { crochetSize = it.toIntOrNull() ?: 0 },
                        label = { Text(text = "Crochet size") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    // buttons
                    DialogButtons(
                        onCancel = onCancel,
                        onConfirm = { onConfirm.invoke(name, description, link, crochetSize) },
                        confirmButtonEnabled = name.isNotBlank(),
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogButtons(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    confirmButtonEnabled: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth().then(modifier),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            onClick = onCancel,
            content = { Text("Cancel") }
        )
        TextButton(
            onClick = onConfirm,
            enabled = confirmButtonEnabled,
            content = { Text("Add") }
        )
    }
}
