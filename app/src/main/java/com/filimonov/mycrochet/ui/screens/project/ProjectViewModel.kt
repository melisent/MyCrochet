package com.filimonov.mycrochet.ui.screens.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.mycrochet.data.LoopType
import com.filimonov.mycrochet.data.Project
import com.filimonov.mycrochet.data.ProjectLine
import com.filimonov.mycrochet.domain.ProjectsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: ProjectsRepository) : ViewModel() {
    private val _project = MutableStateFlow(Project.Empty)
    val project = _project.asStateFlow()

    private val _lines = MutableStateFlow(emptyList<ProjectLine>())
    var lines = _lines.asStateFlow()

    fun load(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            // todo: if null - show error
            (repository.getProject(id) ?: Project.Empty).let {
                _project.value = it

                repository.getProjectLinesById(it.id).collectLatest { lines ->
                    _lines.value = lines.sortedByDescending { line -> line.number }
                }
            }
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
}
