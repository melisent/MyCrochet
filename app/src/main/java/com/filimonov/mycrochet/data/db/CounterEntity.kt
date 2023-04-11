package com.filimonov.mycrochet.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.filimonov.mycrochet.data.LoopType

@Entity
data class CounterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val projectId: Int,
    val number: Int,
    val name: String,
    val startLineCount: Int,
    val endLineCount: Int,
    val loopType: LoopType,
    val crochetSize: Float
)
