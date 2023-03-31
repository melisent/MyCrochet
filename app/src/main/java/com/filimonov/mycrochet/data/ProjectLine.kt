package com.filimonov.mycrochet.data

import java.sql.Timestamp

data class ProjectLine(
    val id: Int,
    val number: Int,
    val name: String,
    var currentLoopCount: Int = 0,
    val maxLoopCount: Int,
    val loopType: LoopType,
    val crochetSize: Int,
    val lastChange: Timestamp,
)
