package com.example.mytodolist.domain.entities

data class TodoItem(
    val name: String,
    val importance: Importance,
    val enabled: Boolean = true,
    var id: Int = UNDEFINED_ID
) {

    companion object {
        const val UNDEFINED_ID = -1
    }
}