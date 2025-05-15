package com.example.whoisvampire.ui.screens.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whoisvampire.data.service.PlayerDao
import com.example.whoisvampire.data.service.RoleDao
import com.example.whoisvampire.data.service.SettingDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val roleDao: RoleDao,
    private val playerDao: PlayerDao,
    private val settingDao: SettingDao,
): ViewModel(){

    init {
        viewModelScope.launch {
            playerDao.getAllPlayers().forEach {
                it.isProtected = false
                it.voteCount = 0
                it.biteCount = 0
                playerDao.updatePlayer(it)
            }
        }
    }

    fun clearRoom(){
        viewModelScope.launch {
            roleDao.deleteAllRoles()
            settingDao.deleteAllSettings()
        }
    }

}