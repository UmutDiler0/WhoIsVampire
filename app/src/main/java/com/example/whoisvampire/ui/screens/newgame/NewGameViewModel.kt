package com.example.whoisvampire.ui.screens.newgame

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
class NewGameViewModel @Inject constructor(
    val playerDao: PlayerDao
) : ViewModel() {


    private var _playerList = MutableStateFlow<List<Player>>(
        emptyList()
    )
    val playerList: StateFlow<List<Player>> = _playerList

    fun getPlayerList() {
        viewModelScope.launch{
            _playerList.value = playerDao.getAllPlayers()
        }
    }




}