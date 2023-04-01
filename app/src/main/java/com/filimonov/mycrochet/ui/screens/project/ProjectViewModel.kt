package com.filimonov.mycrochet.ui.screens.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.mycrochet.data.LoopType
import com.filimonov.mycrochet.data.Project
import com.filimonov.mycrochet.data.ProjectLine
import com.filimonov.mycrochet.domain.ProjectsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: ProjectsRepository) : ViewModel() {
    private val _project = MutableStateFlow(Project.Empty)
    val project = _project.asStateFlow()

    var lines: StateFlow<List<ProjectLine>> = MutableStateFlow(emptyList())

    fun load(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            loadProjectWithLines(id)
        }
    }

    fun addLine(name: String, loopType: LoopType, maxLoopCount: Int, crochetSize: Int) {
        val currentProject = _project.value
        if (currentProject != Project.Empty) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addLine(
                    project = currentProject,
                    line = ProjectLine(
                        id = 0,
                        number = lines.value.size,
                        name = name,
                        currentLoopCount = 0,
                        maxLoopCount = maxLoopCount,
                        loopType = loopType,
                        crochetSize = crochetSize
                    )
                )
            }
        }
    }

    fun increaseLoop(line: ProjectLine) {
        val currentProject = _project.value
        if (currentProject != Project.Empty) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.increaseLoop(currentProject, line)
            }
        }
    }

    fun decreaseLoop(line: ProjectLine) {
        val currentProject = _project.value
        if (currentProject != Project.Empty) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.decreaseLoop(currentProject, line)
            }
        }
    }

    private suspend fun loadProjectWithLines(id: Int) {
        // todo: if null - show error
        (repository.getProject(id) ?: Project.Empty).let {
            _project.value = it
            lines = repository.getProjectLinesById(it.id)
                .map { lines -> lines.sortedByDescending { line -> line.number } }
                .stateIn(viewModelScope)
        }
    }
}
