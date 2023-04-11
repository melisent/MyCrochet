package com.filimonov.mycrochet.data

/**
 * Represents project
 */
data class Project(
    val id: Int,
    val name: String,
    val description: String,
    val link: String,
    val crochetSize: Float
) {
    companion object {
        val Empty = Project(id = -1, name = "", description = "", link = "", crochetSize = 0f)
    }
}
