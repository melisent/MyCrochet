package com.filimonov.mycrochet.di

import androidx.room.Room
import com.filimonov.mycrochet.data.db.ProjectsDatabase
import com.filimonov.mycrochet.domain.ProjectsRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private const val name = "DomainModule"
private const val projectsDatabaseName = "projects_database"

val DomainModule = DI.Module(name) {
    bindSingleton { Room.databaseBuilder(instance(), ProjectsDatabase::class.java, projectsDatabaseName).build() }
    bindSingleton { (instance() as ProjectsDatabase).projectsDao() }
    bindSingleton { ProjectsRepository(instance()) }
}
