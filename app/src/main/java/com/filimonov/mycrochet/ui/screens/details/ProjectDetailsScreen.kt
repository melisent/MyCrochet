package com.filimonov.mycrochet.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.SmartDisplay
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.filimonov.mycrochet.data.Counter
import com.filimonov.mycrochet.data.LoopType
import com.filimonov.mycrochet.ui.screens.details.history.CounterHistoryDialog
import kotlinx.coroutines.delay
import org.kodein.di.compose.rememberViewModel
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailsScreen(projectId: Int, navController: NavHostController) {
    val viewModel: ProjectDetailsViewModel by rememberViewModel()
    val project by viewModel.project.collectAsState()
    val counters by viewModel.counters.collectAsState()

    LaunchedEffect(projectId) { viewModel.load(projectId) }

    var showAddLineDialog by remember { mutableStateOf(false) }
    AddCounterDialog(
        show = showAddLineDialog,
        defaultCrochetSize = project.crochetSize,
        onCancel = { showAddLineDialog = false },
        onConfirm = { name, loopType, startLineCount, endLineCount, crochetSize ->
            viewModel.addCounter(name, loopType, startLineCount, endLineCount, crochetSize)
            showAddLineDialog = false
        }
    )

    var showLineHistoryDialog by remember { mutableStateOf(false) }
    var selectedLineId by remember { mutableStateOf(-1) }
    CounterHistoryDialog(
        show = showLineHistoryDialog,
        counterId = selectedLineId,
        onCancel = { showLineHistoryDialog = false }
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(project.name) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        content = { Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null) }
                    )
                },
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        content = { Icon(imageVector = Icons.Outlined.SmartDisplay, contentDescription = null) }
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddLineDialog = true },
                content = { Icon(imageVector = Icons.Outlined.Add, contentDescription = null) }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.background)) {
            Counters(
                counters = counters,
                onLineClick = {
                    selectedLineId = it.id
                    showLineHistoryDialog = true
                },
                increaseLoopClick = { viewModel.increaseCounter(it) },
                decreaseLoopClick = { viewModel.decreaseCounter(it) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// todo: swipe to delete counters
@Composable
private fun Counters(
    counters: List<Counter>,
    modifier: Modifier = Modifier,
    onLineClick: (Counter) -> Unit,
    increaseLoopClick: (Counter) -> Unit,
    decreaseLoopClick: (Counter) -> Unit
) {
    var currentTime by remember { mutableStateOf(0L) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay(5.seconds)
        }
    }

    LazyColumn(modifier.then(Modifier.padding(vertical = 8.dp))) {
        items(counters) {
            CounterItem(
                counter = it,
                currentTime = currentTime,
                onClick = { onLineClick.invoke(it) },
                increaseLoopClick = { increaseLoopClick.invoke(it) },
                decreaseLoopClick = { decreaseLoopClick.invoke(it) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CounterItem(
    counter: Counter,
    currentTime: Long,
    onClick: () -> Unit,
    increaseLoopClick: () -> Unit,
    decreaseLoopClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(88.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 24.dp)
        ) {
            Column(modifier = Modifier.weight(1f).padding(vertical = 8.dp)) {
                Text(
                    text = counter.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = decreaseLoopClick,
                            content = { Icon(imageVector = Icons.Outlined.Remove, contentDescription = null) }
                        )

                        Text(
                            text = counter.currentLineCount.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        IconButton(
                            onClick = increaseLoopClick,
                            content = { Icon(imageVector = Icons.Outlined.Add, contentDescription = null) }
                        )
                    }

                    Text(
                        text = counter.endLineCount.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (counter.currentLineCount < counter.endLineCount) {
                    Text(
                        text = counter.changedAt.getModifiedLabel(currentTime),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun LineItemPreview() {
    val time = 0L
    val counter = Counter(
        number = 0,
        name = "first counter",
        currentLineCount = 0,
        startLineCount = 0,
        endLineCount = 10,
        loopType = LoopType.DEFAULT,
        crochetSize = 5f,
        changedAt = time
    )

    CounterItem(
        counter = counter,
        currentTime = time,
        onClick = {  },
        increaseLoopClick = {  },
        decreaseLoopClick = {  }
    )
}
