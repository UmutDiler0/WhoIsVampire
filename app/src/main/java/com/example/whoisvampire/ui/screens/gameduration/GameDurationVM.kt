package com.example.whoisvampire.ui.screens.gameduration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whoisvampire.data.service.PlayerDao
import com.example.whoisvampire.data.service.RoleDao
import com.example.whoisvampire.data.service.SettingDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDurationVM @Inject constructor(
    private val roleDao: RoleDao,
    private val playerDao: PlayerDao,
    private val settingDao: SettingDao,
): ViewModel() {

    fun clearRoom(){
        viewModelScope.launch {
            roleDao.deleteAllRoles()
            settingDao.deleteAllSettings()
        }
    }

}