package com.example.mytodolist.domain.usecases

import com.example.mytodolist.domain.entities.TodoItem
import com.example.mytodolist.domain.repository.Repository

class AddTodoItemUseCase(private val repository: Repository) {

    suspend operator fun invoke(todoItem: TodoItem) {
        repository.addTodoItem(todoItem)
    }
}