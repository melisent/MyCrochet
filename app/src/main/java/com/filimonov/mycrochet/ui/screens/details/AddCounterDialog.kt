package com.filimonov.mycrochet.ui.screens.details

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
import com.filimonov.mycrochet.ui.screens.isPositiveFloat
import com.filimonov.mycrochet.ui.screens.isPositiveInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCounterDialog(
    show: Boolean,
    defaultCrochetSize: Float = 0f,
    onCancel: () -> Unit,
    onConfirm: (name: String, loopType: LoopType, startLineCount: Int, endLineCount: Int, crochetSize: Float) -> Unit
) {
    if (show) {
        Dialog(onDismissRequest = onCancel) {
            var name by remember { mutableStateOf("") }
            val loopType = remember { mutableStateOf(LoopType.DEFAULT) }
            var startLineCount by remember { mutableStateOf("") }
            var endLineCount by remember { mutableStateOf("") }
            var crochetSize by remember { mutableStateOf(if (defaultCrochetSize > 0) defaultCrochetSize.toString() else "") }

            Card(
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // headline
                    Text(
                        text = "Add counter",
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
                        value = startLineCount,
                        onValueChange = { if (it.isPositiveInt() || it.isBlank()) startLineCount = it },
                        label = { Text(text = "Start line") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        isError = !startLineCount.isPositiveInt() && startLineCount.isNotBlank(),
                        supportingText = {
                            if (!startLineCount.isPositiveInt() && startLineCount.isNotBlank()) {
                                Text(text = "Must be positive")
                            }
                        }
                    )

                    OutlinedTextField(
                        value = endLineCount,
                        onValueChange = { if (it.isPositiveInt() || it.isBlank()) endLineCount = it },
                        label = { Text(text = "End line") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        isError = !endLineCount.isPositiveInt(),
                        supportingText = {
                            if (!endLineCount.isPositiveInt()) {
                                Text(text = "Must not be empty")
                            }
                        }
                    )

                    OutlinedTextField(
                        value = crochetSize,
                        onValueChange = { if (it.isPositiveFloat() || it.isBlank()) crochetSize = it },
                        label = { Text(text = "Crochet size") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        isError = !crochetSize.isPositiveFloat(),
                        supportingText = {
                            if (!crochetSize.isPositiveFloat()) {
                                Text(text = "Must not be empty")
                            }
                        }
                    )

                    // buttons
                    DialogButtons(
                        onCancel = onCancel,
                        onConfirm = {
                            val loopTypeResult = loopType.value
                            val startLineCountResult = startLineCount.toIntOrNull() ?: 0
                            val endLineCountResult = endLineCount.toIntOrNull() ?: 0
                            val crochetSizeResult = crochetSize.toFloatOrNull() ?: 0f
                            onConfirm.invoke(name, loopTypeResult, startLineCountResult, endLineCountResult, crochetSizeResult)
                        },
                        confirmButtonEnabled = ((name.isNotBlank() && (endLineCount.toIntOrNull() ?: 0) > 0 && (crochetSize.toFloatOrNull() ?: 0f) > 0)),
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
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
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
    AddCounterDialog(show = true, onCancel = {}, onConfirm = { _, _, _, _, _ -> })
}
