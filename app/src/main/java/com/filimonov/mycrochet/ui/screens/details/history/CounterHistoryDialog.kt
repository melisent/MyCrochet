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
import com.filimonov.mycrochet.data.CounterHistory
import org.kodein.di.compose.rememberViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CounterHistoryDialog(
    show: Boolean,
    counterId: Int,
    onCancel: () -> Unit,
) {
    if (show) {
        val viewModel: CounterHistoryViewModel by rememberViewModel()
        val history by viewModel.history.collectAsState()

        LaunchedEffect(counterId) { viewModel.load(counterId) }

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
private fun HistoryList(list: List<CounterHistory>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(list) { HistoryLine(it) }
    }
}

@Composable
private fun HistoryLine(counterHistory: CounterHistory) {
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
                    text = "${counterHistory.count} lines",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(end = 16.dp)
                )

                Text(
                    text = "changed at ${counterHistory.changedAt.format()}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}

private val HistoryTimeFormatter = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.ENGLISH)
private fun Long.format(): String = HistoryTimeFormatter.format(Date(this))

@Preview
@Composable
private fun LineHistoryDialogPreview() {
    val history = listOf(
        CounterHistory(0, 1000L),
        CounterHistory(0, 2000L),
        CounterHistory(0, 3000L),
        CounterHistory(0, 4000L),
        CounterHistory(0, 5000L),
        CounterHistory(0, 6000L),
        CounterHistory(0, 7000L),
        CounterHistory(0, 8000L),
        CounterHistory(0, 9000L),
    )

    HistoryList(list = history)
}
