package com.example.whoisvampire.data.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.whoisvampire.data.model.Game

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM game")
    suspend fun getAllGame(): List<Game>

    @Query("DELETE FROM game")
    suspend fun deleteAllGame()
}