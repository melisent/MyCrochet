package com.filimonov.mycrochet.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.mycrochet.data.LoopType
import com.filimonov.mycrochet.data.Project
import com.filimonov.mycrochet.data.Counter
import com.filimonov.mycrochet.domain.ProjectsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: ProjectsRepository) : ViewModel() {
    private val _project = MutableStateFlow(Project.Empty)
    val project = _project.asStateFlow()

    private val _counters = MutableStateFlow(emptyList<Counter>())
    var counters = _counters.asStateFlow()

    fun load(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            // todo: if null - show error
            (repository.getProjectById(id) ?: Project.Empty).let {
                _project.value = it

                repository.getCountersById(it.id).collectLatest { counterList ->
                    _counters.value = counterList.sortedByDescending { counter -> counter.number }
                }
            }
        }
    }

    fun addCounter(name: String, loopType: LoopType, startLineCount: Int, endLineCount: Int, crochetSize: Float) {
        val currentProject = _project.value
        if (currentProject != Project.Empty) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addCounter(
                    project = currentProject,
                    counter = Counter(
                        number = counters.value.size,
                        name = name,
                        currentLineCount = startLineCount,
                        startLineCount = startLineCount,
                        endLineCount = endLineCount,
                        loopType = loopType,
                        crochetSize = crochetSize
                    )
                )
            }
        }
    }

    fun increaseCounter(counter: Counter) {
        val currentProject = _project.value
        if (currentProject != Project.Empty) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.increaseCounter(counter)
            }
        }
    }

    fun decreaseCounter(counter: Counter) {
        val currentProject = _project.value
        if (currentProject != Project.Empty) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.decreaseCounter(counter)
            }
        }
    }
}
