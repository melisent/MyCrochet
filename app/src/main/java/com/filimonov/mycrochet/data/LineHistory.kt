package com.filimonov.mycrochet.data

import java.sql.Timestamp

data class LineHistory(
    val count: Int,
    val changedAt: Timestamp
)
