package com.example.whoisvampire.ui.screens.gamesetting

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
class GameSettingVM @Inject constructor(
    private val playerDao: PlayerDao,
    private val roleDao: RoleDao,
    private val settingDao: SettingDao
): ViewModel() {

    private var _settings = MutableStateFlow<List<Settings>>(emptyList())
    val settings: StateFlow<List<Settings>> get() = _settings

    private var _playerList = MutableStateFlow<List<Player>>(emptyList())
    val playerList: StateFlow<List<Player>> get() = _playerList

    private var _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> get() = _isLoaded

    private var _gameRoles = MutableStateFlow<List<Roles>>(emptyList())


    init {
        viewModelScope.launch {
            _settings.value = settingsList.settingsList
            matchPlayerWithRoles()
            _isLoaded.value = true
        }
    }

    fun changeSettingToList(settings: Settings) {
        viewModelScope.launch {
            _settings.value.forEach { setting ->
                if(setting.name == settings.name) {
                    setting.isChecked = settings.isChecked
                }
            }
        }
    }

    private fun matchPlayerWithRoles(){
        viewModelScope.launch {
            _playerList.value = playerDao.getAllPlayers()
            _gameRoles.value = roleDao.getAllRoles()
            _playerList.value.forEach{ player ->
                val currentRole = _gameRoles.value.random()
                _gameRoles.value -= currentRole
                player.role = currentRole.name
                playerDao.updatePlayer(player)
            }
        }
    }

    fun insertSetting() {
        viewModelScope.launch {
            settingDao.insertSettings(_settings.value)
        }
    }

    fun deleteAllRoles() {
        viewModelScope.launch {
            roleDao.deleteAllRoles()
        }
    }
}