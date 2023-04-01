package com.filimonov.mycrochet.ui.screens.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.SmartDisplay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.filimonov.mycrochet.data.ProjectLine
import org.kodein.di.compose.rememberViewModel
import java.sql.Timestamp

@Composable
fun ProjectScreen(id: Int) {
    val viewModel: ProjectViewModel by rememberViewModel()
    val project by viewModel.project.collectAsState()
    val lines by viewModel.lines.collectAsState()

    val timerViewModel: TimerViewModel by rememberViewModel()
    val currentTime by timerViewModel.current.collectAsState()

    LaunchedEffect(id) { viewModel.load(id) }

    var showAddLineDialog by remember { mutableStateOf(false) }

    AddLineDialog(
        show = showAddLineDialog,
        onCancel = { showAddLineDialog = false },
        onConfirm = { name, loopType, maxLoopCount, crochetSize ->
            viewModel.addLine(name, loopType, maxLoopCount, crochetSize)
            showAddLineDialog = false
        }
    )

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Toolbar(
            projectName = project.name,
            onAddLineClick = { showAddLineDialog = true }
        )

        Lines(
            lines = lines,
            currentTime = currentTime,
            increaseLoopClick = { viewModel.increaseLoop(it) },
            decreaseLoopClick = { viewModel.decreaseLoop(it) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun Toolbar(projectName: String, onAddLineClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.surface)
    ) {
        IconButton(
            onClick = onAddLineClick,
            content = { Icon(imageVector = Icons.Outlined.Add, contentDescription = null) }
        )

        Text(text = projectName, style = MaterialTheme.typography.bodyLarge)

        IconButton(
            onClick = { /*TODO*/ },
            content = { Icon(imageVector = Icons.Outlined.SmartDisplay, contentDescription = null) }
        )
    }
}

@Composable
private fun Lines(
    lines: List<ProjectLine>,
    currentTime: Timestamp,
    modifier: Modifier = Modifier,
    increaseLoopClick: (ProjectLine) -> Unit,
    decreaseLoopClick: (ProjectLine) -> Unit
) {
    LazyColumn(modifier) {
        items(lines) {
            Line(
                line = it,
                currentTime = currentTime,
                increaseLoopClick = { increaseLoopClick.invoke(it) },
                decreaseLoopClick = { decreaseLoopClick.invoke(it) }
            )
        }
    }
}

@Composable
private fun Line(
    line: ProjectLine,
    currentTime: Timestamp,
    increaseLoopClick: () -> Unit,
    decreaseLoopClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        Text(text = line.name, style = MaterialTheme.typography.bodyLarge)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = decreaseLoopClick,
                    content = { Icon(imageVector = Icons.Outlined.Remove, contentDescription = null)}
                )

                Text(text = line.currentLoopCount.toString(), style = MaterialTheme.typography.bodyMedium)

                IconButton(
                    onClick = increaseLoopClick,
                    content = { Icon(imageVector = Icons.Outlined.Add, contentDescription = null)}
                )
            }

            Text(text = line.maxLoopCount.toString(), style = MaterialTheme.typography.bodyLarge)
        }

        if (line.currentLoopCount < line.maxLoopCount) {
            val modified = line.lastChange.getDifferenceAgo(currentTime)
            Text(text = "modified $modified", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ProjectScreenPreview() {
//    val repository = remember { ProjectsRepository() }
//    val project = repository.getProject(0) ?: Project.Empty
//    val lines = project.lines
//
//    val timerViewModel = remember { TimerViewModel() }
//    val currentTime by timerViewModel.current.collectAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        Toolbar(projectName = project.name)
//        Lines(lines = lines, currentTime = currentTime, modifier = Modifier.weight(1f))
//    }
}
