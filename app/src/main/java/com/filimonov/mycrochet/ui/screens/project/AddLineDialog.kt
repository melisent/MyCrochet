package com.filimonov.mycrochet.ui.screens.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.filimonov.mycrochet.data.LoopType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLineDialog(
    show: Boolean,
    defaultCrochetSize: Int = 0,
    onCancel: () -> Unit,
    onConfirm: (name: String, loopType: LoopType, maxLoopCount: Int, crochetSize: Int) -> Unit
) {
    if (show) {
        Dialog(onDismissRequest = onCancel) {
            var name by remember { mutableStateOf("") }
            val loopType = remember { mutableStateOf(LoopType.DEFAULT) }
            var maxLoopCount by remember { mutableStateOf(0) }
            var crochetSize by remember { mutableStateOf(defaultCrochetSize) }

            Card(
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp)
                ) {
                    // headline
                    Text(
                        text = "Add line",
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

                    LoopTypeDropdown(loopType = loopType)

                    OutlinedTextField(
                        value = maxLoopCount.toString(),
                        onValueChange = { maxLoopCount = it.toIntOrNull() ?: 0 },
                        label = { Text(text = "Loop count") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        isError = maxLoopCount < 1,
                        supportingText = { if (maxLoopCount < 1) Text(text = "Must not be empty") }
                    )

                    OutlinedTextField(
                        value = crochetSize.toString(),
                        onValueChange = { crochetSize = it.toIntOrNull() ?: 0 },
                        label = { Text(text = "Crochet size") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        isError = crochetSize < 1,
                        supportingText = { if (crochetSize < 1) Text(text = "Must not be empty") }
                    )

                    // buttons
                    DialogButtons(
                        onCancel = onCancel,
                        onConfirm = { onConfirm.invoke(name, loopType.value, maxLoopCount, crochetSize) },
                        confirmButtonEnabled = (name.isNotBlank() && maxLoopCount > 0 && crochetSize > 0),
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoopTypeDropdown(loopType: MutableState<LoopType>) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = loopType.value.toString(),
            onValueChange = {},
            label = { Text("Loop type") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            LoopType.values().forEach {
                DropdownMenuItem(
                    text = { Text(text = it.toString()) },
                    onClick = {
                        loopType.value = it
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
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

@Preview(showBackground = true)
@Composable
private fun AddLineDialogPreview() {
    AddLineDialog(show = true, onCancel = {}, onConfirm = {_, _, _, _ -> })
}
