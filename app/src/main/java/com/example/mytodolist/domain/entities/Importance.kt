package com.example.mytodolist.domain.entities

import kotlin.random.Random

enum class Importance {
    HIGH, MEDIUM, LOW;

    companion object {

        private val values = values().toList()
        private val length = values.size

        fun giveRandom(): Importance {
            return values[Random.nextInt(length)]
        }
    }
}