package com.example.whoisvampire.ui.screens.nightresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.data.service.PlayerDao
import com.example.whoisvampire.data.service.RoleDao
import com.example.whoisvampire.data.service.SettingDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NightResultViewModel @Inject constructor(
    private val playerDao: PlayerDao,
    private val settingDao: SettingDao,
    private val roleDao: RoleDao
): ViewModel() {

    private var _playerList = MutableStateFlow<List<Player>>(emptyList())
    val playerList: StateFlow<List<Player>> get() = _playerList

    private var _isListLoaded = MutableStateFlow<Boolean>(false)
    val isListLoaded: StateFlow<Boolean> get() = _isListLoaded

    private var _mostBitedPlayer = MutableStateFlow<Player>(Player.empty())
    val mostBitedPlayer: StateFlow<Player> get() = _mostBitedPlayer

    init {
        viewModelScope.launch {
            var biteCount = 0
            _playerList.value = playerDao.getAllPlayers()

            var mostBited: Player? = null

            _playerList.value.forEach { player ->
                if (player.biteCount > biteCount && player.isAlive) {
                    biteCount = player.biteCount
                    mostBited = player
                }
            }

            mostBited?.let { player ->
                if (!player.isProtected) {
                    player.isAlive = false
                    playerDao.updatePlayer(player)
                    _mostBitedPlayer.value = player
                } else {
                    _mostBitedPlayer.value = Player.empty()
                }
            }

            _playerList.value = playerDao.getAllPlayers()
            _isListLoaded.value = true
        }
    }


    fun clearProtection(){
        _playerList.value.forEach {
            it.isProtected = false
        }
    }

    fun clearRoom(){
        viewModelScope.launch {
            settingDao.deleteAllSettings()
            roleDao.deleteAllRoles()
        }
    }
}