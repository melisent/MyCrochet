package com.filimonov.mycrochet.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CounterHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val counterId: Int,
    val count: Int,
    val changedAt: Long
)
