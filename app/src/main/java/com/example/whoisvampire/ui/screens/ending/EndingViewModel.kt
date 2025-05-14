package com.example.whoisvampire.ui.screens.ending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.data.model.Roles
import com.example.whoisvampire.data.service.PlayerDao
import com.example.whoisvampire.data.service.RoleDao
import com.example.whoisvampire.data.service.SettingDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EndingViewModel @Inject constructor(
    private val playerDao: PlayerDao,
    private val settingDao: SettingDao,
    private val roleDao: RoleDao
): ViewModel() {

   private var _winnerList = MutableStateFlow<List<Player>>(emptyList())
   val winnerList: StateFlow<List<Player>> get() = _winnerList

    private var _winnerType = MutableStateFlow<Roles>(Roles(name = "", count = 0,image = 0))
    val winnerType: StateFlow<Roles> get() = _winnerType

    private var _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> get() = _isLoaded

    init {
        viewModelScope.launch {
            var vampire = 0
            var villager = 0
            playerDao.getAllPlayers().forEach { player ->
                if(player.isAlive){
                    if(player.role == "Vampire") vampire++
                    else villager++
                }
            }
            if(vampire == 0) _winnerType.value.copy(name = "Villager")
            else _winnerType.value.copy(name = "Vampire")
            _isLoaded.value = true
        }
    }

    fun resetGame(){
        viewModelScope.launch {
            playerDao.deleteAllPlayers()
            settingDao.deleteAllSettings()
            roleDao.deleteAllRoles()
        }
    }


}