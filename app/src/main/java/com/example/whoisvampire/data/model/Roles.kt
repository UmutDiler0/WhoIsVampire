package com.example.whoisvampire.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Roles(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    var count: Int,
    var image: Int,
)