package com.example.whoisvampire.ui.screens.addplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.data.service.PlayerDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlayerViewModel @Inject constructor(
    val playerDao: PlayerDao
): ViewModel() {

    private var _player = MutableStateFlow<Player>(Player.empty())
    val player : StateFlow<Player> get() = _player

    fun addPlayer(player: Player) {
        viewModelScope.launch {
            _player.value = player
            playerDao.insertPlayer(player)
        }
    }
}