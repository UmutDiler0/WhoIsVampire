package com.example.whoisvampire.ui.screens.gameduration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whoisvampire.common.util.settingsList
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.data.model.Roles
import com.example.whoisvampire.data.model.Settings
import com.example.whoisvampire.data.service.PlayerDao
import com.example.whoisvampire.data.service.RoleDao
import com.example.whoisvampire.data.service.SettingDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDurationVM @Inject constructor(
    private val roleDao: RoleDao,
    private val playerDao: PlayerDao,
    private val settingDao: SettingDao,
): ViewModel() {

    private var _playerList = MutableStateFlow<List<Player>>(emptyList())
    val playerList: StateFlow<List<Player>> get() = _playerList

    private var _gameRoles = MutableStateFlow<List<Roles>>(emptyList())

    private var _isLoaded = MutableStateFlow(false)
    val isLoaded = _isLoaded

    init {
           matchPlayerWithRoles()
        _isLoaded.value = true
    }

    fun clearRoom(){
        viewModelScope.launch {
            roleDao.deleteAllRoles()
            settingDao.deleteAllSettings()
        }
    }

    private fun matchPlayerWithRoles() {
        viewModelScope.launch {
            _playerList.value = playerDao.getAllPlayers()
            _gameRoles.value = roleDao.getAllRoles()

            if (_gameRoles.value.isEmpty()) {
                Log.e("matchPlayerWithRoles", "Rol listesi boş!")
                return@launch
            }

            _playerList.value.forEach { player ->
                val currentRole = _gameRoles.value.random()
                _gameRoles.value -= currentRole
                player.role = currentRole.name
                playerDao.updatePlayer(player)
            }
        }
    }

}