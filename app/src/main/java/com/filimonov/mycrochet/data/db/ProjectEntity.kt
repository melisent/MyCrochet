package com.filimonov.mycrochet.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val description: String,
    val link: String,
    val crochetSize: Int
)
