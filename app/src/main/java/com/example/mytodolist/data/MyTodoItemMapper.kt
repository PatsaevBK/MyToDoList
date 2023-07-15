package com.example.mytodolist.data

import com.example.mytodolist.domain.entities.TodoItem

class MyTodoItemMapper {

    fun todoItemToEntity(todoItem: TodoItem) = TodoItemDbModel(
        todoItem.id,
        todoItem.name,
        todoItem.importance,
        todoItem.enabled
    )

    fun entityToTodoItem(todoItemDbModel: TodoItemDbModel) = TodoItem(
        todoItemDbModel.name,
        todoItemDbModel.importance,
        todoItemDbModel.enabled,
        todoItemDbModel.id
    )

    fun listDbModelToTodoItem(listDbModel: List<TodoItemDbModel>) = listDbModel.map {
        entityToTodoItem(it)
    }
}