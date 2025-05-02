package com.example.whoisvampire.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.data.service.PlayerDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val playerDao: PlayerDao
): ViewModel(){

    private var _currentPlayer = MutableStateFlow<Player>(
        Player.empty()
    )
    val currentPlayer: StateFlow<Player> get() = _currentPlayer

    fun deletePlayer(player: Player){
        viewModelScope.launch {
            playerDao.deletePlayer(player)
        }
    }

    fun getPlayerById(id: Int){
        viewModelScope.launch {
           _currentPlayer.value = playerDao.getPlayerById(id) ?: Player.empty()
        }
    }

    fun updatePlayer(player: Player){
        viewModelScope.launch {
            playerDao.updatePlayer(player)
        }
    }
}