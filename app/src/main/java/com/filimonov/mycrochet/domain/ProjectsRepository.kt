package com.filimonov.mycrochet.domain

import com.filimonov.mycrochet.data.CounterHistory
import com.filimonov.mycrochet.data.Project
import com.filimonov.mycrochet.data.Counter
import com.filimonov.mycrochet.data.db.CounterHistoryEntity
import com.filimonov.mycrochet.data.db.ProjectEntity
import com.filimonov.mycrochet.data.db.CounterEntity
import com.filimonov.mycrochet.data.db.ProjectWithCountersEntity
import com.filimonov.mycrochet.data.db.ProjectsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.sql.Timestamp

class ProjectsRepository(private val dao: ProjectsDao) {
    fun getProjects(): Flow<List<Project>> {
        return dao.getProjects().map {
            it.map { project -> project.toUi() }
        }
    }

    suspend fun getProjectById(id: Int): Project? = dao.getProjectById(id)?.toUi()

    suspend fun addProject(project: Project) {
        dao.addProject(
            ProjectEntity(
                id = project.id,
                name = project.name,
                description = project.description,
                link = project.link,
                crochetSize = project.crochetSize
            )
        )
    }

    fun getCountersById(projectId: Int): Flow<List<Counter>> {
        return dao.getCountersWithHistoryByProjectId(projectId).map { list ->
            list.map { counterWithHistory ->
                val counter = counterWithHistory.counter
                val history = counterWithHistory.history

                val (count, lastChange) =
                    history.maxByOrNull {
                        it.changedAt
                    }.let {
                        (it?.count ?: counter.startLineCount) to (it?.changedAt ?: 0)
                    }

                Counter(
                    id = counter.id,
                    number = counter.number,
                    name = counter.name,
                    currentLineCount = count,
                    startLineCount = counter.startLineCount,
                    endLineCount = counter.endLineCount,
                    loopType = counter.loopType,
                    crochetSize = counter.crochetSize,
                    changedAt = Timestamp(lastChange)
                )
            }
        }
    }

    fun getCounterHistoryByCounterId(counterId: Int): Flow<List<CounterHistory>> {
        return dao.getCounterHistoryByCounterId(counterId).map { list ->
            list.map { CounterHistory(count = it.count, changedAt = Timestamp(it.changedAt)) }
        }
    }

    suspend fun addCounter(project: Project, counter: Counter) {
        dao.addCounter(
            CounterEntity(
                id = counter.id,
                projectId = project.id,
                number = counter.number,
                name = counter.name,
                startLineCount = counter.startLineCount,
                endLineCount = counter.endLineCount,
                loopType = counter.loopType,
                crochetSize = counter.crochetSize
            )
        )
    }

    suspend fun increaseCounter(counter: Counter) {
        if (counter.currentLineCount < counter.endLineCount) {
            dao.updateCounterHistory(
                CounterHistoryEntity(0, counter.id, counter.currentLineCount + 1, System.currentTimeMillis())
            )
        }
    }

    suspend fun decreaseCounter(counter: Counter) {
        if (counter.currentLineCount > counter.startLineCount) {
            dao.updateCounterHistory(
                CounterHistoryEntity(0, counter.id, counter.currentLineCount - 1, System.currentTimeMillis())
            )
        }
    }
}

private fun ProjectWithCountersEntity.toUi() =
    Project(
        id = project.id,
        name = project.name,
        description = project.description,
        link = project.link,
        crochetSize = project.crochetSize
    )

private fun ProjectEntity.toUi() =
    Project(
        id = id,
        name = name,
        description = description,
        link = link,
        crochetSize = crochetSize
    )

private fun CounterEntity.toUi() =
    Counter(
        id = id,
        name = name,
        number = number,
        startLineCount = startLineCount,
        endLineCount = endLineCount,
        loopType = loopType,
        crochetSize = crochetSize
    )

private fun Counter.toEntity(projectId: Int) =
    CounterEntity(
        id = id,
        projectId = projectId,
        name = name,
        number = number,
        startLineCount = startLineCount,
        endLineCount = endLineCount,
        loopType = loopType,
        crochetSize = crochetSize
    )
