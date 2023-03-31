package com.filimonov.mycrochet.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.filimonov.mycrochet.data.LoopType

@Entity
data class ProjectLineEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val projectId: Int,
    val number: Int,
    val name: String,
    val currentLoopCount: Int,
    val maxLoopCount: Int,
    val loopType: LoopType,
    val crochetSize: Int,
    val lastChange: Long
)