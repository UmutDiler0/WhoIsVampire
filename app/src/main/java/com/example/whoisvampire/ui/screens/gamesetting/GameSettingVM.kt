package com.example.whoisvampire.ui.screens.gamesetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whoisvampire.common.util.settingsList
import com.example.whoisvampire.data.model.Settings
import com.example.whoisvampire.data.service.RoleDao
import com.example.whoisvampire.data.service.SettingDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSettingVM @Inject constructor(
    val roleDao: RoleDao,
    val settingDao: SettingDao
): ViewModel() {

    private var _settings = MutableStateFlow<List<Settings>>(emptyList())
    val settings: StateFlow<List<Settings>> get() = _settings

    init {
        viewModelScope.launch {
            _settings.value = settingsList.settingsList
        }
    }

    fun changeSettingToList(settings: Settings) {
        viewModelScope.launch {
            _settings.value.forEach { setting ->
                if(setting.name == settings.name) {
                    setting.isChecked = settings.isChecked
                }
            }
        }
    }

    fun insertSetting() {
        viewModelScope.launch {
            settingDao.insertSettings(_settings.value)
        }
    }

    fun deleteAllRoles() {
        viewModelScope.launch {
            roleDao.deleteAllRoles()
        }
    }
}