package com.filimonov.mycrochet.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class CounterWithHistoryEntity(
    @Embedded
    val counter: CounterEntity,

    @Relation(parentColumn = "id", entityColumn = "counterId")
    val history: List<CounterHistoryEntity>
)
