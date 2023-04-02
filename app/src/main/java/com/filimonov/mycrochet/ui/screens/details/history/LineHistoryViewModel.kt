package com.filimonov.mycrochet.ui.screens.details.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.mycrochet.data.LineHistory
import com.filimonov.mycrochet.domain.ProjectsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LineHistoryViewModel(private val repository: ProjectsRepository) : ViewModel() {
    private val _history = MutableStateFlow(emptyList<LineHistory>())
    val history = _history.asStateFlow()

    fun load(lineId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLineHistoryByLineId(lineId).collectLatest { list ->
                _history.value = list.sortedByDescending { it.changedAt.time }
            }
        }
    }
}
