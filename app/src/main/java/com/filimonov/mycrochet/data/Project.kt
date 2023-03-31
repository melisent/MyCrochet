package com.filimonov.mycrochet.data

data class Project(
    val id: Int,
    val name: String,
    val description: String,
    val link: String,
    val crochetSize: Int,
    val lines: List<ProjectLine>
) {
    companion object {
        val Empty = Project(id = -1, name = "", description = "", link = "", crochetSize = 0, lines = emptyList())
    }
}
