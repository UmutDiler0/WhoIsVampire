package com.example.whoisvampire.ui.screens.vote

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whoisvampire.R
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
class VoteViewModel @Inject constructor(
    private val roleDao: RoleDao,
    private val settingDao: SettingDao,
    private val playerDao: PlayerDao
): ViewModel() {

    private var _playerList = MutableStateFlow<List<Player>>(emptyList())
    val playerList: StateFlow<List<Player>> get() = _playerList

    private var _newPlayerList = MutableStateFlow<List<Player>>(emptyList())
    val newPlayerList: StateFlow<List<Player>> get() = _newPlayerList

    private var _isListLoaded = MutableStateFlow<Boolean>(false)
    val isListLoaded: StateFlow<Boolean> get() = _isListLoaded

    private var _player = MutableStateFlow<Player>(Player.empty())
    val player: StateFlow<Player> get() = _player

    private var _playerNumber = MutableStateFlow<Int>(0)
    val playerNumber: StateFlow<Int> = _playerNumber

    private var _selectedPlayer = MutableStateFlow<Player?>(null)
    val selectedPlayer: StateFlow<Player?> = _selectedPlayer

    private var _votedPlayer = MutableStateFlow<Player>(Player.empty())

    init {
        viewModelScope.launch {
            val allPlayers = playerDao.getAllPlayers()
            _playerList.value = allPlayers
            _newPlayerList.value = allPlayers.filter { it.isAlive }
            _isListLoaded.value = true
            getCurrentPlayer(_playerNumber.value) // İlk oyuncuyu burada yükle
        }
    }

    fun getCurrentPlayer(index: Int) {
        if (_isListLoaded.value && index < _newPlayerList.value.size) {
            val current = _newPlayerList.value[index]
            _player.value = current
        }
    }


    fun addVote(player: Player?) {
        if (player != null) {
            player.voteCount++
            _selectedPlayer.value = null
        }
    }

    fun resultOfVote() {
        viewModelScope.launch {
            val mostVotedPlayer = _playerList.value.maxByOrNull { it.voteCount }
            playerDao.updatePlayer(mostVotedPlayer ?: Player.empty())
        }
    }

    fun selectPlayer(player: Player) {
        _selectedPlayer.value = player
    }

    fun addPlayerNumber() {
        _playerNumber.value++
        getCurrentPlayer(_playerNumber.value)
    }

    fun clearRoom(){
        viewModelScope.launch {
            roleDao.deleteAllRoles()
            settingDao.deleteAllSettings()
        }
    }

}