package com.example.mytodolist.domain.usecases

import com.example.mytodolist.domain.entities.TodoItem
import com.example.mytodolist.domain.repository.Repository

class GetTodoItemUseCase(private val repository: Repository) {

    suspend operator fun invoke(id: Int): TodoItem {
        return repository.getTodoItem(id)
    }
}