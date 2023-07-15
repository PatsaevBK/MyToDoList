package com.example.mytodolist.data

import androidx.room.TypeConverter
import com.example.mytodolist.domain.entities.Importance

class Converters {
    @TypeConverter
    fun fromInt(value: Int?): Importance? {
        return value?.let {
            val array = Importance.values()
            array[it]
        }
    }

    @TypeConverter
    fun importanceToInt(importance: Importance): Int {
        return importance.ordinal
    }
}