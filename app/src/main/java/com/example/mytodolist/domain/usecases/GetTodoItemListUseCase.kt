package com.example.mytodolist.domain.usecases

import androidx.lifecycle.LiveData
import com.example.mytodolist.domain.entities.TodoItem
import com.example.mytodolist.domain.repository.Repository

class GetTodoItemListUseCase(private val repository: Repository) {

    operator fun invoke(): LiveData<List<TodoItem>> {
        return repository.getTodoItemList()
    }
}