package com.example.whoisvampire.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class Game(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayTime: String,
    val dayNumber: Int,
)