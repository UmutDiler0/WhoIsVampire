package com.example.whoisvampire.ui.screens.gameduration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whoisvampire.common.util.settingsList
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
class GameDurationVM @Inject constructor(
    private val roleDao: RoleDao,
    private val playerDao: PlayerDao,
    private val settingDao: SettingDao,
): ViewModel() {

    private var _isLoaded = MutableStateFlow(false)
    val isLoaded = _isLoaded

    init {
        viewModelScope.launch {
            playerDao.getAllPlayers().forEach {
                it.voteCount = 0
                it.isProtected = false
                it.biteCount = 0
            }
        }
        _isLoaded.value = true
    }

    fun clearRoom(){
        viewModelScope.launch {
            roleDao.deleteAllRoles()
            settingDao.deleteAllSettings()
        }
    }



}