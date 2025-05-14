package com.example.whoisvampire

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.whoisvampire.data.model.Game
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.data.model.Roles
import com.example.whoisvampire.data.model.Settings
import com.example.whoisvampire.data.service.GameDao
import com.example.whoisvampire.data.service.PlayerDao
import com.example.whoisvampire.data.service.RoleDao
import com.example.whoisvampire.data.service.SettingDao
import javax.inject.Singleton

@Database(
    entities = [Player::class, Roles::class, Settings::class, Game::class],
    version = 10,
    exportSchema = false
)
@Singleton
abstract class AppDatabase: RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun roleDao(): RoleDao
    abstract fun settingDao(): SettingDao
    abstract fun gameDao(): GameDao
}