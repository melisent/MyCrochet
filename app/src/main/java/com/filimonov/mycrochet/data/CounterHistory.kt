package com.filimonov.mycrochet.data

import java.sql.Timestamp

data class CounterHistory(
    val count: Int,
    val changedAt: Timestamp
)
