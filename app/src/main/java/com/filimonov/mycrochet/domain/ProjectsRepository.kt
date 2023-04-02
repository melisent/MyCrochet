package com.filimonov.mycrochet.domain

import com.filimonov.mycrochet.data.Project
import com.filimonov.mycrochet.data.ProjectLine
import com.filimonov.mycrochet.data.db.LineHistoryEntity
import com.filimonov.mycrochet.data.db.ProjectEntity
import com.filimonov.mycrochet.data.db.ProjectLineEntity
import com.filimonov.mycrochet.data.db.ProjectWithLinesEntity
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

    fun getProjectLinesById(projectId: Int): Flow<List<ProjectLine>> {
        return dao.getLinesByProjectId(projectId).map { list ->
            list.map { lineWithHistory ->
                val line = lineWithHistory.line
                val history = lineWithHistory.history

                val (count, lastChange) =
                    history.maxByOrNull {
                        it.changedAt
                    }.let {
                        (it?.count ?: 0) to (it?.changedAt ?: 0)
                    }

                ProjectLine(
                    id = line.id,
                    number = line.number,
                    name = line.name,
                    currentLoopCount = count,
                    maxLoopCount = line.maxLoopCount,
                    loopType = line.loopType,
                    crochetSize = line.crochetSize,
                    changedAt = Timestamp(lastChange)
                )
            }
        }
    }

    suspend fun addLine(project: Project, line: ProjectLine) {
        dao.addLine(
            ProjectLineEntity(
                id = line.id,
                projectId = project.id,
                number = line.number,
                name = line.name,
                maxLoopCount = line.maxLoopCount,
                loopType = line.loopType,
                crochetSize = line.crochetSize
            )
        )
    }

    suspend fun increaseLoop(line: ProjectLine) {
        if (line.currentLoopCount < line.maxLoopCount) {
            dao.updateLineHistory(
                LineHistoryEntity(0, line.id, line.currentLoopCount + 1, System.currentTimeMillis())
            )
        }
    }

    suspend fun decreaseLoop(line: ProjectLine) {
        if (line.currentLoopCount > 0) {
            dao.updateLineHistory(
                LineHistoryEntity(0, line.id, line.currentLoopCount - 1, System.currentTimeMillis())
            )
        }
    }
}

private fun ProjectWithLinesEntity.toUi() =
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

private fun ProjectLineEntity.toUi() =
    ProjectLine(
        id = id,
        name = name,
        number = number,
        maxLoopCount = maxLoopCount,
        loopType = loopType,
        crochetSize = crochetSize
    )

private fun ProjectLine.toEntity(projectId: Int) =
    ProjectLineEntity(
        id = id,
        projectId = projectId,
        name = name,
        number = number,
        maxLoopCount = maxLoopCount,
        loopType = loopType,
        crochetSize = crochetSize
    )
