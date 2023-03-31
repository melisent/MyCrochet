package com.filimonov.mycrochet.di

import com.filimonov.mycrochet.domain.ProjectsRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton

private const val name = "DomainModule"

val DomainModule = DI.Module(name) {
    bindSingleton { ProjectsRepository() }
}
