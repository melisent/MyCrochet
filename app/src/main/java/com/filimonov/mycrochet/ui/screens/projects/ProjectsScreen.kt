package com.filimonov.mycrochet.ui.screens.projects

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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.filimonov.mycrochet.data.Project
import com.filimonov.mycrochet.navigateToProjectDetails
import org.kodein.di.compose.rememberViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(navController: NavHostController) {
    val viewModel: ProjectsViewModel by rememberViewModel()
    val projects by viewModel.projects.collectAsState()

    var showAddProjectDialog by remember { mutableStateOf(false) }

    AddProjectDialog(
        show = showAddProjectDialog,
        onCancel = { showAddProjectDialog = false },
        onConfirm = { name, description, link, crochetSize ->
            viewModel.addProject(name, description, link, crochetSize)
            showAddProjectDialog = false
        }
    )

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("MyCrochet") }) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddProjectDialog = true },
                content = { Icon(imageVector = Icons.Outlined.Add, contentDescription = null) }
            )
        }
    ) { padding ->
        Projects(
            projects = projects,
            onClick = { projectId -> navController.navigateToProjectDetails(projectId) },
            modifier = Modifier.fillMaxSize().padding(padding)
        )
    }
}

@Composable
private fun Projects(
    projects: List<Project>,
    onClick: (projectId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.then(Modifier.padding(vertical = 8.dp))) {
        items(projects) {
            ProjectItem(project = it, onClick = { onClick.invoke(it.id) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProjectItem(project: Project, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
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
                    text = project.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(end = 16.dp)
                )

                Text(
                    text = "01.04.2023", // todo:
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}

@Preview
@Composable
private fun ProjectItemPreview() {
    val project = Project(0, "Test project first", "", "", 5)
    ProjectItem(project = project, onClick = {})
}
