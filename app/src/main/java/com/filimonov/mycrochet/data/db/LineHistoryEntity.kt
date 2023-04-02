package com.filimonov.mycrochet.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LineHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val lineId: Int,
    val count: Int,
    val changedAt: Long
)
