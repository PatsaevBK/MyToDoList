package com.example.mytodolist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyTodoItemDao {


    @Query("SELECT * FROM todo_items")
    fun getTodoItemList(): LiveData<List<TodoItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodoItem(todoItem: TodoItemDbModel)

    @Query("DELETE FROM todo_items WHERE id= :id")
    suspend fun deleteTodoItem(id: Int)

    @Query("SELECT * FROM todo_items WHERE id = :id LIMIT 1")
    suspend fun getTodoItem(id: Int): TodoItemDbModel

}