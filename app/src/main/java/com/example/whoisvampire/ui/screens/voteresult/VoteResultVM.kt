package com.example.whoisvampire.ui.screens.voteresult

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.data.service.PlayerDao
import com.example.whoisvampire.data.service.RoleDao
import com.example.whoisvampire.data.service.SettingDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoteResultVM @Inject constructor(
    private val playerDao: PlayerDao,
    private val settingDao: SettingDao,
    private val roleDao: RoleDao
): ViewModel() {

    private var _playerList = MutableStateFlow<List<Player>>(emptyList())
    val playerList: StateFlow<List<Player>> get()= _playerList

    private var _player = MutableStateFlow<Player>(Player.empty())
    val player: StateFlow<Player> get() = _player

    private var _isListLoaded = MutableStateFlow<Boolean>(false)
    val isListLoaded: StateFlow<Boolean> get() = _isListLoaded

    val aliveVampireCount: Int
        get() = _playerList.value.count { it.isAlive && it.role == "Vampir" }

    val aliveVillagerCount: Int
        get() = _playerList.value.count { it.isAlive && it.role != "Vampir" }

    init {
        viewModelScope.launch {
            val players = playerDao.getAllPlayers()
            _playerList.value = players

            val alivePlayers = players.filter { it.isAlive }

            // En yüksek oyu bul
            val maxVote = alivePlayers.maxOfOrNull { it.voteCount } ?: 0

            // Bu oyu alan kaç kişi var?
            val topVotedPlayers = alivePlayers.filter { it.voteCount == maxVote }

            // Eğer sadece 1 kişi aldıysa -> onu öldür
            if (maxVote > 0 && topVotedPlayers.size == 1) {
                val votedOutPlayer = topVotedPlayers.first().copy(isAlive = false)
                playerDao.updatePlayer(votedOutPlayer)
                _player.value = votedOutPlayer
            } else {
                // Eşitlik var ya da hiç oy kullanılmadıysa -> kimse ölmesin
                _player.value = Player.empty()
            }
            _playerList.value -= topVotedPlayers

            _isListLoaded.value = true
        }
    }




}