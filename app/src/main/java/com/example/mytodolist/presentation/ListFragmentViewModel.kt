package com.example.mytodolist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mytodolist.data.RepositoryImpl
import com.example.mytodolist.domain.entities.TodoItem
import com.example.mytodolist.domain.usecases.DeleteTodoItemUseCase
import com.example.mytodolist.domain.usecases.EditTodoItemUseCase
import com.example.mytodolist.domain.usecases.GetTodoItemListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ListFragmentViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val getTodoItemListUseCase = GetTodoItemListUseCase(repository)
    private val deleteTodoItemUseCase = DeleteTodoItemUseCase(repository)
    private val editTodoItemUseCase = EditTodoItemUseCase(repository)

    val scope = CoroutineScope(Dispatchers.IO)

    val todoList = getTodoItemListUseCase()

    fun changeEnableState(todoItem: TodoItem) {
        scope.launch {
            val newItem = todoItem.copy(enabled = !todoItem.enabled)
            editTodoItemUseCase(newItem)
        }
    }

    fun deleteTodoItem(todoItem: TodoItem) {
        scope.launch {
            deleteTodoItemUseCase(todoItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}