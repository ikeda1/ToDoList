package com.ikeda.todolist.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "task_table")
data class Task (
    @PrimaryKey(autoGenerate = true) val id: Int,
    var title: String,
    var description: String,
    var time: String,
    var date: String,
    var priority: Int,
    var timestamp: Long
): Parcelable