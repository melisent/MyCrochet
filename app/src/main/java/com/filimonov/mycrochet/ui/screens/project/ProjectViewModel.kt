package com.filimonov.mycrochet.ui.screens.project

import androidx.lifecycle.ViewModel
import com.filimonov.mycrochet.data.Project
import com.filimonov.mycrochet.data.ProjectLine
import com.filimonov.mycrochet.domain.ProjectsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProjectViewModel(private val repository: ProjectsRepository) : ViewModel() {
    private val _project = MutableStateFlow(Project.Empty)
    val project = _project.asStateFlow()

    private val _lines = MutableStateFlow<List<ProjectLine>>(emptyList())
    val lines = _lines.asStateFlow()

    fun load(id: Int) {
        (repository.getProject(id) ?: Project.Empty).let {
            _project.value = it
            _lines.value = it.lines
        }
    }

    fun addLine(line: ProjectLine) {
        val currentProject = _project.value
        if (currentProject != Project.Empty) {
            repository.addLine(currentProject, line)
        }
    }

    fun addLoop(line: ProjectLine) {
        // todo
    }

    fun removeLoop(line: ProjectLine) {
        // todo
    }
}
