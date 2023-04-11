package com.filimonov.mycrochet.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProjectEntity::class, CounterEntity::class, CounterHistoryEntity::class], version = 1)
abstract class ProjectsDatabase : RoomDatabase() {
    abstract fun projectsDao(): ProjectsDao
}
