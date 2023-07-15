package com.example.mytodolist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mytodolist.domain.entities.TodoItem
import com.example.mytodolist.domain.repository.Repository

class RepositoryImpl(
    application: Application
) : Repository {

    private val dao = AppDatabase.getInstance(application).myTodoItemDao()
    private val mapper = MyTodoItemMapper()

    override fun addTodoItem(todoItem: TodoItem) {
        dao.addTodoItem(mapper.todoItemToEntity(todoItem))
    }

    override fun deleteTodoItem(todoItem: TodoItem) {
        dao.deleteTodoItem(todoItem.id)
    }

    override fun getTodoItem(id: Int): TodoItem {
        return mapper.entityToTodoItem(dao.getTodoItem(id))
    }

    override fun editTodoItem(todoItem: TodoItem) {
        dao.addTodoItem(mapper.todoItemToEntity(todoItem))
    }

    override fun getTodoItemList(): LiveData<List<TodoItem>> {
        return MediatorLiveData<List<TodoItem>>().apply {
            addSource(dao.getTodoItemList()) {
                value = mapper.listDbModelToTodoItem(it)
            }
        }
    }

}