package com.example.mytodolist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.mytodolist.domain.entities.TodoItem

class TodoItemListDIffCallback(
    private val oldList: List<TodoItem>,
    private val newList: List<TodoItem>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}