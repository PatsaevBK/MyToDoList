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

    override suspend fun addTodoItem(todoItem: TodoItem) {
        dao.addTodoItem(mapper.todoItemToEntity(todoItem))
    }

    override suspend fun deleteTodoItem(todoItem: TodoItem) {
        dao.deleteTodoItem(todoItem.id)
    }

    override suspend fun getTodoItem(id: Int): TodoItem {
        return mapper.entityToTodoItem(dao.getTodoItem(id))
    }

    override suspend fun editTodoItem(todoItem: TodoItem) {
        dao.addTodoItem(mapper.todoItemToEntity(todoItem))
    }

    override fun getTodoItemList(): LiveData<List<TodoItem>> {
        return MediatorLiveData<List<TodoItem>>().apply {
            addSource(dao.getTodoItemList()) {
                postValue(mapper.listDbModelToTodoItem(it))
            }
        }
    }

}