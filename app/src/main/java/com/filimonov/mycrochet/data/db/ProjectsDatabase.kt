package com.filimonov.mycrochet.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProjectEntity::class, ProjectLineEntity::class, LineHistoryEntity::class], version = 2)
abstract class ProjectsDatabase : RoomDatabase() {
    abstract fun projectsDao(): ProjectsDao
}
