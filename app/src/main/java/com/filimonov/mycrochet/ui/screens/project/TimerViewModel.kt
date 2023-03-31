package com.filimonov.mycrochet.ui.screens.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.sql.Timestamp
import kotlin.time.Duration.Companion.seconds

class TimerViewModel : ViewModel() {
    private val _current = MutableStateFlow(Timestamp(System.currentTimeMillis()))
    val current = _current.asStateFlow()

    init {
        viewModelScope.launch {
            while (isActive) {
                _current.value = Timestamp(System.currentTimeMillis())
                delay(1.seconds)
            }
        }
    }
}