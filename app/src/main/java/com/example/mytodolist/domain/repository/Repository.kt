package com.example.mytodolist.domain.repository

import androidx.lifecycle.LiveData
import com.example.mytodolist.domain.entities.TodoItem

interface Repository {

    suspend fun addTodoItem(todoItem: TodoItem)

    suspend fun deleteTodoItem(todoItem: TodoItem)

    suspend fun getTodoItem(id: Int): TodoItem

    suspend fun editTodoItem(todoItem: TodoItem)

    fun getTodoItemList(): LiveData<List<TodoItem>>


}