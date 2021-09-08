package com.ikeda.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ikeda.todolist.database.Task
import com.ikeda.todolist.database.TaskDatabase
import com.ikeda.todolist.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {

    private val  taskDao = TaskDatabase.getDatabase(application).taskDao()
    private val repository: TaskRepository = TaskRepository(taskDao)

    val getAllTasks: LiveData<List<Task>> = repository.getAllTasks()
    val getAllPriorityTasks: LiveData<List<Task>> = repository.getAllPriorityTasks()

    fun insert(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(task)
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(task)
        }
    }

    fun update(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(task)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Task>> {
        return repository.searchDatabase(searchQuery)
    }

}