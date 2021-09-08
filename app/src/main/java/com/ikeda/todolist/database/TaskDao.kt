package com.ikeda.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY timestamp DESC")
    fun getAllTasks(): LiveData<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(task: Task)

    @Query("select * from task_table order by priority asc")
    fun getAllPriorityTasks(): LiveData<List<Task>>

    @Query("select * from task_table where title like :searchQuery order by date desc")
    fun searchDatabase(searchQuery: String): LiveData<List<Task>>

}