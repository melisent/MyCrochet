package com.filimonov.mycrochet.ui.screens.details.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.filimonov.mycrochet.data.LineHistory
import org.kodein.di.compose.rememberViewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun LineHistoryDialog(
    show: Boolean,
    lineId: Int,
    onCancel: () -> Unit,
) {
    if (show) {
        val viewModel: LineHistoryViewModel by rememberViewModel()
        val history by viewModel.history.collectAsState()

        LaunchedEffect(lineId) { viewModel.load(lineId) }

        Dialog(onDismissRequest = onCancel) {
            Card(
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                    Text(
                        text = "Line history",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    HistoryList(
                        list = history,
                        modifier = Modifier.weight(1f)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = onCancel,
                            content = { Text("Close") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryList(list: List<LineHistory>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(list) { HistoryLine(it) }
    }
}

@Composable
private fun HistoryLine(lineHistory: LineHistory) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(56.dp) // height - material 1-line list item height
    ) {
        // material paddings
        Column(modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 24.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().weight(1f).padding(vertical = 8.dp)
            ) {
                Text(
                    text = "${lineHistory.count} loops",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(end = 16.dp)
                )

                Text(
                    text = "changed at ${lineHistory.changedAt.format()}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}

private val HistoryTimeFormatter = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.ENGLISH)
private fun Timestamp.format(): String = HistoryTimeFormatter.format(this)

@Preview
@Composable
private fun LineHistoryDialogPreview() {
    val time = Timestamp(0)
    val history = listOf(
        LineHistory(0, time.apply { this.time += 0 * 1000 }),
        LineHistory(0, time.apply { this.time += 1 * 1000 }),
        LineHistory(0, time.apply { this.time += 2 * 1000 }),
        LineHistory(0, time.apply { this.time += 3 * 1000 }),
        LineHistory(0, time.apply { this.time += 4 * 1000 }),
        LineHistory(0, time.apply { this.time += 5 * 1000 }),
        LineHistory(0, time.apply { this.time += 6 * 1000 }),
        LineHistory(0, time.apply { this.time += 7 * 1000 }),
        LineHistory(0, time.apply { this.time += 8 * 1000 }),
        LineHistory(0, time.apply { this.time += 9 * 1000 })
    )

    HistoryList(list = history)
}
