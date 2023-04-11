package com.filimonov.mycrochet.data

import java.sql.Timestamp

/**
 * Represents counter of lines
 */
data class Counter(
    val id: Int = 0,
    val number: Int,
    val name: String,
    val currentLineCount: Int = 0,
    val startLineCount: Int,
    val endLineCount: Int,
    val loopType: LoopType,
    val crochetSize: Float,
    val changedAt: Timestamp = Timestamp(0)
)
