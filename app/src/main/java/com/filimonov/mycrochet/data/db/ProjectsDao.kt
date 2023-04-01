package com.filimonov.mycrochet.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectsDao {
    @Transaction
    @Query("SELECT * FROM projectEntity WHERE id = :id")
    suspend fun getProjectWithLinesById(id: Int): ProjectWithLinesEntity?

    @Query("SELECT * FROM projectEntity WHERE id = :id")
    suspend fun getProjectById(id: Int): ProjectEntity?

    @Query("SELECT * FROM projectLineEntity WHERE projectId = :projectId")
    fun getProjectLinesById(projectId: Int): Flow<List<ProjectLineEntity>>

    @Insert
    suspend fun addLine(line: ProjectLineEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLine(line: ProjectLineEntity)
}
