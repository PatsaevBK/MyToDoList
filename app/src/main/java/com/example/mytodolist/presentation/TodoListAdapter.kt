package com.example.mytodolist.presentation

import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.mytodolist.R
import com.example.mytodolist.domain.entities.Importance
import com.example.mytodolist.domain.entities.TodoItem

class TodoListAdapter :
    androidx.recyclerview.widget.ListAdapter<TodoItem, TodoListViewHolder>(TodoItemDIffCallback()) {


    var onClickListener: ((TodoItem) -> Unit)? = null
    var onLongClickListener: ((TodoItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        Log.d("adapter", "onCreateViewHolder()")
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.todoitem,
            parent,
            false
        )
        return TodoListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val todoItem = getItem(position)
        Log.d("adapter", "onBindViewHolder $todoItem")
        val color = when (todoItem.importance) {
            Importance.HIGH -> R.color.importance_high
            Importance.LOW -> R.color.importance_low
            Importance.MEDIUM -> R.color.importance_medium
        }
        with(holder) {
            layout.setBackgroundColor(ContextCompat.getColor(view.context, color))
            if (!todoItem.enabled) {
                layout.backgroundTintList =
                    ContextCompat.getColorStateList(holder.view.context, R.color.disabled_tint)
                layout.backgroundTintMode = PorterDuff.Mode.MULTIPLY
            }
            name.text = todoItem.name
            view.setOnClickListener {
                onClickListener?.invoke(todoItem)
            }
            view.setOnLongClickListener {
                onLongClickListener?.invoke(todoItem)
                true
            }
        }
    }

    override fun onViewRecycled(holder: TodoListViewHolder) {
        super.onViewRecycled(holder)
        with(holder) {
            name.text = ""
            layout.backgroundTintList = null
        }
    }


}