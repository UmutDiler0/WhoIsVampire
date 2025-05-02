package com.example.whoisvampire.data.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.whoisvampire.data.model.Player

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: Player)

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertPlayers(playerList: List<Player>)

    @Update
    suspend fun updatePlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)

    @Query("SELECT * FROM Player")
    suspend fun getAllPlayers(): List<Player>

    @Query("SELECT * FROM Player WHERE id = :id")
    suspend fun getPlayerById(id: Int): Player?

    @Query("DELETE FROM Player")
    suspend fun deleteAllPlayers()

}