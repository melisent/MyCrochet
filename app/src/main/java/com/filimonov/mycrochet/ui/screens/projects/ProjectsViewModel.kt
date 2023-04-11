package com.filimonov.mycrochet.ui.screens.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.mycrochet.data.Project
import com.filimonov.mycrochet.domain.ProjectsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProjectsViewModel(private val repository: ProjectsRepository) : ViewModel() {
    val projects: StateFlow<List<Project>> = repository.getProjects().stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList())

    fun addProject(name: String, description: String, link: String, crochetSize: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProject(
                Project(id = 0, name = name, description = description, link = link, crochetSize = crochetSize)
            )
        }
    }
}
