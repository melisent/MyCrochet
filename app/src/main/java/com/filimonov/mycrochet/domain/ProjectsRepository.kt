package com.filimonov.mycrochet.domain

import com.filimonov.mycrochet.data.Project
import com.filimonov.mycrochet.data.ProjectLine
import com.filimonov.mycrochet.data.db.ProjectEntity
import com.filimonov.mycrochet.data.db.ProjectLineEntity
import com.filimonov.mycrochet.data.db.ProjectWithLinesEntity
import com.filimonov.mycrochet.data.db.ProjectsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.sql.Timestamp

class ProjectsRepository(private val dao: ProjectsDao) {
    suspend fun getProject(id: Int): Project? = dao.getProjectById(id)?.toUi()

    fun getProjectLinesById(projectId: Int): Flow<List<ProjectLine>> {
        return dao.getProjectLinesById(projectId).map {
            it.map { line -> line.toUi() }
        }
    }

    suspend fun addLine(project: Project, line: ProjectLine) {
        dao.addLine(
            ProjectLineEntity(
                id = line.id,
                projectId = project.id,
                number = line.number,
                name = line.name,
                currentLoopCount = line.currentLoopCount,
                maxLoopCount = line.maxLoopCount,
                loopType = line.loopType,
                crochetSize = line.crochetSize,
                lastChange = System.currentTimeMillis()
            )
        )
    }

    suspend fun increaseLoop(project: Project, line: ProjectLine) {
        if (line.currentLoopCount < line.maxLoopCount) {
            val entity = line.toEntity(project.id).copy(currentLoopCount = line.currentLoopCount + 1, lastChange = System.currentTimeMillis())
            dao.updateLine(entity)
        }
    }

    suspend fun decreaseLoop(project: Project, line: ProjectLine) {
        if (line.currentLoopCount > 0) {
            val entity = line.toEntity(project.id).copy(currentLoopCount = line.currentLoopCount - 1, lastChange = System.currentTimeMillis())
            dao.updateLine(entity)
        }
    }
}

private fun ProjectWithLinesEntity.toUi() =
    Project(
        id = project.id,
        name = project.name,
        description = project.description,
        link = project.link,
        crochetSize = project.crochetSize,
        lines = lines.map { it.toUi() }
    )

private fun ProjectEntity.toUi() =
    Project(
        id = id,
        name = name,
        description = description,
        link = link,
        crochetSize = crochetSize,
        lines = emptyList()
    )

private fun ProjectLineEntity.toUi() =
    ProjectLine(
        id = id,
        name = name,
        number = number,
        currentLoopCount = currentLoopCount,
        maxLoopCount = maxLoopCount,
        loopType = loopType,
        crochetSize = crochetSize,
        lastChange = Timestamp(lastChange)
    )

private fun ProjectLine.toEntity(projectId: Int) =
    ProjectLineEntity(
        id = id,
        projectId = projectId,
        name = name,
        number = number,
        currentLoopCount = currentLoopCount,
        maxLoopCount = maxLoopCount,
        loopType = loopType,
        crochetSize = crochetSize,
        lastChange = lastChange.time
    )
