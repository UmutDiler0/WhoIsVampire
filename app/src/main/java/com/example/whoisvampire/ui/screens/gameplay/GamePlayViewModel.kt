package com.example.whoisvampire.ui.screens.gameplay

import androidx.compose.runtime.MutableState
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class GamePlayViewModel @Inject constructor(
    private val playerDao: PlayerDao,
    private val settingsDao: SettingDao,
    private val roleDao: RoleDao
) : ViewModel() {


    private var _player = MutableStateFlow<Player>(Player.empty())
    val player: StateFlow<Player> get() = _player

    private var _isListLoaded = MutableStateFlow<Boolean>(false)
    val isListLoaded: StateFlow<Boolean> get() = _isListLoaded

    private var _playersList = MutableStateFlow<List<Player>>(emptyList())
    val playerList: StateFlow<List<Player>> get() = _playersList

    private var _isSelected = MutableStateFlow<Boolean>(false)
    val isSelected: StateFlow<Boolean> get() = _isSelected

    private var _allPlayer = MutableStateFlow<List<Player>>(emptyList())
    val allPlayer: StateFlow<List<Player>> get() = _allPlayer

    private val _selectedPlayer = MutableStateFlow<Player?>(null)
    val selectedPlayer: StateFlow<Player?> = _selectedPlayer




    fun updateRoom(player: Player){
        viewModelScope.launch {
            playerDao.updatePlayer(player)
        }
    }

    fun changeSelect(){
        _isSelected.value = !_isSelected.value
    }

    fun selectPlayer(player: Player) {
        _selectedPlayer.value = player
        if(_selectedPlayer.value != null) _selectedPlayer.value!!.biteCount++
    }

    fun selectedBy(choosingPlayer: Player){
        viewModelScope.launch {
            _selectedPlayer.value?.let {
                it.selectedBy = choosingPlayer.name
                it.biteCount++
                playerDao.updatePlayer(it)
            }
        }
    }



    fun getPlayer(id: Int){
        viewModelScope.launch {
            _player.value = playerDao.getPlayerById(id) ?: Player.empty()
            playerDao.getAllPlayers().forEach {
                if(it.isAlive) _playersList.value += it
            }
//            _playersList.value = playerDao.getAllPlayers()
            _playersList.value -= _player.value
            _allPlayer.value = playerDao.getAllPlayers()
            _isListLoaded.value = true
        }
    }
    fun clearRoom(){
        viewModelScope.launch {
            settingsDao.deleteAllSettings()
            roleDao.deleteAllRoles()
        }
    }

}
