package com.filimonov.mycrochet.domain

import com.filimonov.mycrochet.data.LoopType
import com.filimonov.mycrochet.data.Project
import com.filimonov.mycrochet.data.ProjectLine
import java.sql.Timestamp
import kotlin.random.Random

class ProjectsRepository {
    private val projects = mutableListOf(
        Project(id = 0, description = "", name = "Project 1", link = "", crochetSize = 6, lines = generateLines()),
        Project(id = 1, description = "", name = "Project 2", link = "", crochetSize = 6, lines = generateLines()),
        Project(id = 2, description = "", name = "Project 3", link = "", crochetSize = 6, lines = generateLines()),
        Project(id = 3, description = "", name = "Project 4", link = "", crochetSize = 6, lines = generateLines()),
        Project(id = 4, description = "", name = "Project 5", link = "", crochetSize = 6, lines = generateLines()),
        Project(id = 5, description = "", name = "Project 6", link = "", crochetSize = 6, lines = generateLines())
    )

    fun getProject(id: Int): Project? {
        return projects.find { it.id == id }
    }

    fun addLine(project: Project, line: ProjectLine) {

    }

    private fun generateLines(): List<ProjectLine> {
        return buildList {
            repeat(Random.nextInt(1, 10)) {
                val maxLoopCount = Random.nextInt(1, 100)

                add(
                    ProjectLine(
                        id = it,
                        number = it,
                        name = "Строка ${it + 1}",
                        currentLoopCount = Random.nextInt(maxLoopCount),
                        maxLoopCount = maxLoopCount,
                        loopType = LoopType.DEFAULT,
                        crochetSize = 6,
                        lastChange = Timestamp(System.currentTimeMillis())
                    )
                )
            }
        }
    }
}
