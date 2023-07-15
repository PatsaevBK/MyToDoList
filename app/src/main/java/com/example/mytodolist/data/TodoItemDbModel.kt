package com.example.mytodolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mytodolist.domain.entities.Importance

@Entity(tableName = "todo_items")
data class TodoItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val importance: Importance,
    val enabled: Boolean
)