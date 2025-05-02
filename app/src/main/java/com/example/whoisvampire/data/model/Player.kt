package com.example.whoisvampire.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.whoisvampire.R

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val image: Int,
    val role: String,
    val isAlive: Boolean,
){
    companion object{
        fun empty(): Player{
            return Player(
                id = 0,
                name = "",
                image = R.drawable.ic_launcher_background,
                role = "",
                isAlive = true
            )
        }
    }
}