package com.filimonov.mycrochet.ui.screens.details.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.mycrochet.data.CounterHistory
import com.filimonov.mycrochet.domain.ProjectsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CounterHistoryViewModel(private val repository: ProjectsRepository) : ViewModel() {
    private val _history = MutableStateFlow(emptyList<CounterHistory>())
    val history = _history.asStateFlow()

    fun load(lineId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCounterHistoryByCounterId(lineId).collectLatest { list ->
                _history.value = list.sortedByDescending { it.changedAt.time }
            }
        }
    }
}
