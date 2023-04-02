package com.filimonov.mycrochet.di

import com.filimonov.mycrochet.ui.screens.details.ProjectViewModel
import com.filimonov.mycrochet.ui.screens.details.TimerViewModel
import com.filimonov.mycrochet.ui.screens.details.history.LineHistoryViewModel
import com.filimonov.mycrochet.ui.screens.projects.ProjectsViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private const val name = "ViewModelModule"

val ViewModelModule = DI.Module(name) {
    bindSingleton { TimerViewModel() }
    bindProvider { ProjectViewModel(instance()) }
    bindProvider { ProjectsViewModel(instance()) }
    bindProvider { LineHistoryViewModel(instance()) }
}