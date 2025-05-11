package com.example.whoisvampire.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.whoisvampire.R

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    val image: Int,
    var role: String,
    var voteCount: Int = 0,
    var biteCount: Int = 0,
    var selectedBy: String = "",
    var selectCount: Int = 0,
    var isAlive: Boolean = true,
){
    companion object{
        fun empty(): Player{
            return Player(
                id = 0,
                name = "",
                image = R.drawable.ic_launcher_background,
                role = "",
                isAlive = false
            )
        }
    }
}