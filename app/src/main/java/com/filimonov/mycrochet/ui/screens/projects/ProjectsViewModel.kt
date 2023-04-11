package com.filimonov.mycrochet.ui.screens.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.mycrochet.data.Project
import com.filimonov.mycrochet.domain.ProjectsRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProjectsViewModel(private val repository: ProjectsRepository) : ViewModel() {
    val projects: StateFlow<ImmutableList<Project>> =
        repository.getProjects()
            .map { it.toImmutableList() }
            .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = persistentListOf())

    fun addProject(name: String, description: String, link: String, crochetSize: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProject(
                Project(id = 0, name = name, description = description, link = link, crochetSize = crochetSize)
            )
        }
    }
}
