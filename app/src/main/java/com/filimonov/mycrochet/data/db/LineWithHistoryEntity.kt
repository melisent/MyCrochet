package com.filimonov.mycrochet.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class LineWithHistoryEntity(
    @Embedded
    val line: ProjectLineEntity,

    @Relation(parentColumn = "id", entityColumn = "lineId")
    val history: List<LineHistoryEntity>
)
