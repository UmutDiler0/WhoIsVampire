package com.example.whoisvampire.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.data.model.Roles
import com.example.whoisvampire.data.model.Settings
import com.example.whoisvampire.data.service.PlayerDao
import com.example.whoisvampire.data.service.RoleDao
import com.example.whoisvampire.data.service.SettingDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    private val settingDao: SettingDao,
    private val roleDao: RoleDao,
    private val playerDao: PlayerDao,

    ): ViewModel() {

    private var _playerList = MutableStateFlow<List<Player>>(emptyList())
    val playerList: StateFlow<List<Player>> get() = _playerList



    private var _gameSettings = MutableStateFlow<List<Settings>>(emptyList())
    val gameSettings: StateFlow<List<Settings>> get() = _gameSettings

    private var _gameRoles = MutableStateFlow<List<Roles>>(emptyList())
    val gameRoles: StateFlow<List<Roles>> get() = _gameRoles

    private var _isListLoaded = MutableStateFlow<Boolean>(false)
    val isListLoaded: StateFlow<Boolean> get() = _isListLoaded

    private var _playerNumber = MutableStateFlow<Int>(0)
    val playerNumber: StateFlow<Int> = _playerNumber

    private var _currentPlayer = MutableStateFlow<Player>(Player.empty())
    val currentPlayer: StateFlow<Player> = _currentPlayer

    fun addPlayerNumber() {
        if (_playerNumber.value < _playerList.value.size - 1) {
            _playerNumber.value++
        }
    }

    init {
        viewModelScope.launch {
            playerDao.getAllPlayers().forEach {
                if(it.isAlive) {
                    it.voteCount = 0
                    it.biteCount = 0
                    _playerList.value += it
                }
            }
            _gameSettings.value = settingDao.getAllSettings()
            _gameRoles.value = roleDao.getAllRoles()
            _isListLoaded.value = true
        }
    }

    fun clearAllSetting(){
        viewModelScope.launch {
            settingDao.deleteAllSettings()
            roleDao.deleteAllRoles()
        }
    }
}