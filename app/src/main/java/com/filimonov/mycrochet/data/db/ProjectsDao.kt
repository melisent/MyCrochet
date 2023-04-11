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
    suspend fun getProjectWithCountersById(id: Int): ProjectWithCountersEntity?

    @Query("SELECT * FROM projectEntity")
    fun getProjects(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM projectEntity WHERE id = :id")
    suspend fun getProjectById(id: Int): ProjectEntity?

    @Insert
    suspend fun addProject(project: ProjectEntity)

    @Transaction
    @Query("SELECT * FROM counterEntity WHERE projectId = :projectId")
    fun getCountersWithHistoryByProjectId(projectId: Int): Flow<List<CounterWithHistoryEntity>>

    @Query("SELECT * FROM counterHistoryEntity WHERE counterId = :counterId")
    fun getCounterHistoryByCounterId(counterId: Int): Flow<List<CounterHistoryEntity>>

    @Insert
    suspend fun addCounter(counter: CounterEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCounter(counter: CounterEntity)

    @Insert
    suspend fun updateCounterHistory(history: CounterHistoryEntity)
}
