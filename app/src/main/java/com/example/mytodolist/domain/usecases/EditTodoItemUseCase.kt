package com.example.mytodolist.domain.usecases

import com.example.mytodolist.domain.entities.TodoItem
import com.example.mytodolist.domain.repository.Repository

class EditTodoItemUseCase(private val repository: Repository) {

    operator fun invoke(todoItem: TodoItem) {
        repository.editTodoItem(todoItem)
    }
}