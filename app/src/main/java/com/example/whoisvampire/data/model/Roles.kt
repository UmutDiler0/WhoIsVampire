package com.example.whoisvampire.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Roles")
data class Roles(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    var countOfRoles: Int
)