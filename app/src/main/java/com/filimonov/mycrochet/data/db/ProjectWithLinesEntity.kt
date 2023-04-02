package com.filimonov.mycrochet.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class ProjectWithLinesEntity(
    @Embedded
    val project: ProjectEntity,

    @Relation(parentColumn = "id", entityColumn = "projectId")
    val lines: List<ProjectLineEntity>
)
