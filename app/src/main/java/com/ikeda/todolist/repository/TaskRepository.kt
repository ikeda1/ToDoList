package com.ikeda.todolist.repository

import androidx.lifecycle.LiveData
import com.ikeda.todolist.database.Task
import com.ikeda.todolist.database.TaskDao

class TaskRepository(val taskDao: TaskDao) {

    suspend fun insert(task: Task) = taskDao.insertTask(task)

    suspend fun updateData(task: Task) = taskDao.update(task)

    suspend fun deleteItem(task: Task) = taskDao.delete(task)

    suspend fun deleteAll() {
        taskDao.deleteAll()
    }

    fun getAllTasks(): LiveData<List<Task>> = taskDao.getAllTasks()

    fun getAllPriorityTasks(): LiveData<List<Task>> = taskDao.getAllPriorityTasks()

    fun searchDatabase(searchQuery: String): LiveData<List<Task>> {
        return taskDao.searchDatabase(searchQuery)
    }
}