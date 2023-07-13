package com.example.mytodolist.presentation

import androidx.lifecycle.ViewModel
import com.example.mytodolist.data.RepositoryImpl
import com.example.mytodolist.domain.entities.TodoItem
import com.example.mytodolist.domain.usecases.*

class ListFragmentViewModel : ViewModel() {

    private val repository = RepositoryImpl

    private val getTodoItemListUseCase = GetTodoItemListUseCase(repository)
    private val deleteTodoItemUseCase = DeleteTodoItemUseCase(repository)
    private val editTodoItemUseCase = EditTodoItemUseCase(repository)

    val todoList = getTodoItemListUseCase()

    fun changeEnableState(todoItem: TodoItem) {
        val newItem = todoItem.copy(enabled = !todoItem.enabled)
        editTodoItemUseCase(newItem)
    }

    fun deleteTodoItem(todoItem: TodoItem) = deleteTodoItemUseCase(todoItem)
}