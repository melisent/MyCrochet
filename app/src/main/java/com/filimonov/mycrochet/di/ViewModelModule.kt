package com.filimonov.mycrochet.di

import com.filimonov.mycrochet.ui.screens.details.ProjectDetailsViewModel
import com.filimonov.mycrochet.ui.screens.details.history.CounterHistoryViewModel
import com.filimonov.mycrochet.ui.screens.projects.ProjectsViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

private const val name = "ViewModelModule"

val ViewModelModule = DI.Module(name) {
    bindProvider { ProjectDetailsViewModel(instance()) }
    bindProvider { ProjectsViewModel(instance()) }
    bindProvider { CounterHistoryViewModel(instance()) }
}