package com.example.whoisvampire.data.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.whoisvampire.data.model.Settings

@Dao
interface SettingDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: List<Settings>)

    @Delete
    suspend fun deleteSetting(setting: Settings)

    @Query("DELETE FROM settings")
    suspend fun deleteAllSettings()

    @Query("SELECT * FROM settings")
    suspend fun getAllSettings(): List<Settings>
}