package com.example.mytodolist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mytodolist.data.RepositoryImpl
import com.example.mytodolist.domain.entities.TodoItem
import com.example.mytodolist.domain.usecases.DeleteTodoItemUseCase
import com.example.mytodolist.domain.usecases.EditTodoItemUseCase
import com.example.mytodolist.domain.usecases.GetTodoItemListUseCase

class ListFragmentViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

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