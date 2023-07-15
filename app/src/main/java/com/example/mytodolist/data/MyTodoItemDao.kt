package com.example.mytodolist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyTodoItemDao {


    @Query("SELECT * FROM todo_items")
    fun getTodoItemList(): LiveData<List<TodoItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTodoItem(todoItem: TodoItemDbModel)

    @Query("DELETE FROM todo_items WHERE id= :id")
    fun deleteTodoItem(id: Int)

    @Query("SELECT * FROM todo_items WHERE id = :id LIMIT 1")
    fun getTodoItem(id: Int): TodoItemDbModel

}