package com.filimonov.mycrochet.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface ProjectsDao {
    @Transaction
    @Query("SELECT * FROM projectEntity WHERE id = :id")
    suspend fun getProjectWithLines(id: Int): ProjectWithLinesEntity?

    @Insert
    suspend fun addLine(line: ProjectLineEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLine(line: ProjectLineEntity)
}
