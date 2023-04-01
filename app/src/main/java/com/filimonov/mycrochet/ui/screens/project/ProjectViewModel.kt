package com.filimonov.mycrochet.ui.screens.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.mycrochet.data.Project
import com.filimonov.mycrochet.data.ProjectLine
import com.filimonov.mycrochet.domain.ProjectsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: ProjectsRepository) : ViewModel() {
    private val _project = MutableStateFlow(Project.Empty)
    val project = _project.asStateFlow()

    private val _lines = MutableStateFlow<List<ProjectLine>>(emptyList())
    val lines = _lines.asStateFlow()

    fun load(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            loadProjectWithLines(id)
        }
    }

    fun addLine(line: ProjectLine) {
        val currentProject = _project.value
        if (currentProject != Project.Empty) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addLine(currentProject, line)
            }
        }
    }

    fun increaseLoop(line: ProjectLine) {
        val currentProject = _project.value
        if (currentProject != Project.Empty) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.increaseLoop(currentProject, line)
                loadProjectWithLines(currentProject.id) // todo: reload only this line
            }
        }
    }

    fun decreaseLoop(line: ProjectLine) {
        val currentProject = _project.value
        if (currentProject != Project.Empty) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.decreaseLoop(currentProject, line)
                loadProjectWithLines(currentProject.id) // todo: reload only this line
            }
        }
    }

    private suspend fun loadProjectWithLines(id: Int) {
        // todo: if null - show error
        (repository.getProject(id) ?: Project.Empty).let {
            _project.value = it
            _lines.value = it.lines.reversed()
        }
    }
}
