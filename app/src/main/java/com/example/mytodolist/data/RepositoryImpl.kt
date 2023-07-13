package com.example.mytodolist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytodolist.domain.entities.Importance
import com.example.mytodolist.domain.entities.TodoItem
import com.example.mytodolist.domain.repository.Repository

object RepositoryImpl : Repository {

    private val todoItemList = sortedSetOf(
        Comparator<TodoItem> { o1, o2 ->
            o1.id.compareTo(o2.id)
        })

    private val _todoItemListLD = MutableLiveData<List<TodoItem>>()
    val todoItemListLD: LiveData<List<TodoItem>>
        get() = _todoItemListLD

    private var autoIncrementId = 0

    init {
        for (i in 0..100) {
            addTodoItem(
                TodoItem(
                    i.toString(),
                    Importance.giveRandom(),
                    true,
                    TodoItem.UNDEFINED_ID
                )
            )
        }
    }

    override fun addTodoItem(todoItem: TodoItem) {
        if (todoItem.id == TodoItem.UNDEFINED_ID) {
            todoItem.id = autoIncrementId++
        }
        todoItemList += todoItem
        updateListLD()
    }

    override fun deleteTodoItem(todoItem: TodoItem) {
        todoItemList -= todoItem
        updateListLD()
    }

    override fun getTodoItem(id: Int): TodoItem {
        return todoItemList.find { it.id == id } ?: throw RuntimeException("Unknown id: $id")
    }

    override fun editTodoItem(todoItem: TodoItem) {
        val oldTodoItem = getTodoItem(todoItem.id)
        todoItemList -= oldTodoItem
        addTodoItem(todoItem)
    }

    override fun getTodoItemList(): LiveData<List<TodoItem>> {
        return todoItemListLD
    }

    private fun updateListLD() {
        _todoItemListLD.value = todoItemList.toList()
    }

}