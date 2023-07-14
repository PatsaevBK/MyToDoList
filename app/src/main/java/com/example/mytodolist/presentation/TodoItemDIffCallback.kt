package com.example.mytodolist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.mytodolist.domain.entities.TodoItem

class TodoItemDIffCallback(
): DiffUtil.ItemCallback<TodoItem>() {

    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem == newItem
    }

}