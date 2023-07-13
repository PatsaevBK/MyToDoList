package com.example.mytodolist.domain.usecases

import com.example.mytodolist.domain.entities.TodoItem
import com.example.mytodolist.domain.repository.Repository

class DeleteTodoItemUseCase(private val repository: Repository) {

    operator fun invoke(todoItem: TodoItem) {
        repository.deleteTodoItem(todoItem)
    }
}