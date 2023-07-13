package com.example.mytodolist.domain.repository

import androidx.lifecycle.LiveData
import com.example.mytodolist.domain.entities.TodoItem

interface Repository {

    fun addTodoItem(todoItem: TodoItem)

    fun deleteTodoItem(todoItem: TodoItem)

    fun getTodoItem(id: Int): TodoItem

    fun editTodoItem(todoItem: TodoItem)

    fun getTodoItemList(): LiveData<List<TodoItem>>


}