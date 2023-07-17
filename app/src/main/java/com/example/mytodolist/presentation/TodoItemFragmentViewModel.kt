package com.example.mytodolist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytodolist.data.RepositoryImpl
import com.example.mytodolist.domain.entities.Importance
import com.example.mytodolist.domain.entities.TodoItem
import com.example.mytodolist.domain.usecases.AddTodoItemUseCase
import com.example.mytodolist.domain.usecases.EditTodoItemUseCase
import com.example.mytodolist.domain.usecases.GetTodoItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class TodoItemFragmentViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _errorInputTask = MutableLiveData<Boolean>()
    val errorInputTask: LiveData<Boolean>
        get() = _errorInputTask

    private val _readyToClose = MutableLiveData<Unit>()
    val readyToClose: LiveData<Unit>
        get() = _readyToClose

    private val _todoItem = MutableLiveData<TodoItem>()
    val todoItem: LiveData<TodoItem>
        get() = _todoItem

    private val repository = RepositoryImpl(application)

    private val addTodoItemUseCase = AddTodoItemUseCase(repository)
    private val editTodoItemUseCase = EditTodoItemUseCase(repository)
    private val getTodoItemUseCase = GetTodoItemUseCase(repository)

    fun editTodoItem(name: String, importance: Importance) {
        val parseName = parseName(name)
        val isValidInput = isValidInput(parseName, importance)
        if (isValidInput) {
            _todoItem.value?.let {
                scope.launch {
                    val todoItem = TodoItem(parseName, importance, id = it.id)
                    editTodoItemUseCase(todoItem)
                    finishWork()
                }
            }
        } else {
            _errorInputTask.value = true
        }
    }

    fun addTodoItem(name: String, importance: Importance) {
        val parseName = parseName(name)
        val isValidInput = isValidInput(parseName, importance)
        if (isValidInput) {
            scope.launch {
                val todoItem = TodoItem(parseName, importance)
                addTodoItemUseCase(todoItem)
                finishWork()
            }
        } else {
            _errorInputTask.value = true
        }
    }

    fun getTodoItemWithId(id: Int) {
        scope.launch {
            val item = getTodoItemUseCase(id)
            _todoItem.postValue(item)
        }
    }

    fun resetErrorInputTask() {
        _errorInputTask.value = false
    }

    private fun parseName(name: String): String {
        return name.trim()
    }

    private fun isValidInput(name: String, importance: Importance): Boolean {
        var result = true
        if (name.isBlank()) {
            result = false
        }
        if (importance !in Importance.values()) {
            result = false
        }
        return result
    }

    private fun finishWork(){
        _readyToClose.postValue(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}