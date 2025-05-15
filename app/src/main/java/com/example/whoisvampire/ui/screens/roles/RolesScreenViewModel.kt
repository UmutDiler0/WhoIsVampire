package com.example.whoisvampire.ui.screens.roles

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whoisvampire.common.util.roles
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.data.model.Roles
import com.example.whoisvampire.data.service.PlayerDao
import com.example.whoisvampire.data.service.RoleDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RolesScreenViewModel @Inject constructor(
    val playerDao: PlayerDao,
    val roleDao: RoleDao,
): ViewModel(){

    private var _rolesList = MutableStateFlow<List<Roles>>(roles.rolesList)
    val roleList: StateFlow<List<Roles>> get() = _rolesList

    private var _gameRoleList = MutableStateFlow<List<Roles>>(emptyList())
    val gameRoleList: StateFlow<List<Roles>> get() = _gameRoleList

    private var _totalRoleNumber = MutableStateFlow<Int>(0)
    val totalRoleNumber: StateFlow<Int> = _gameRoleList.map { list ->
        list.sumOf { it.count }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    private var _roleBound = MutableStateFlow<Int>(0)
    val roleBound: StateFlow<Int> get() = _roleBound

    private var _isNavAvailable = MutableStateFlow<Boolean>(false)
    val isNavAvailable: StateFlow<Boolean> get() = _isNavAvailable

    private var _playerList = MutableStateFlow<List<Player>>(emptyList())

    init {
        viewModelScope.launch {
            _playerList.value = playerDao.getAllPlayers()
            _roleBound.value = playerDao.getAllPlayers().size
        }
    }

    fun deleteAllPlayers(){
        viewModelScope.launch {
            playerDao.deleteAllPlayers()
            roleDao.deleteAllRoles()
        }
    }



    fun checkNavConditions() {
        val playerCount = _playerList.value.size
        val currentList = _gameRoleList.value
        val totalRoles = currentList.sumOf { it.count }
        val vampirCount = currentList.find { it.name.equals("Vampir", ignoreCase = true) }?.count ?: 0
        val othersCount = totalRoles - vampirCount

        _isNavAvailable.value = vampirCount < othersCount && totalRoles == playerCount
    }

    fun matchPlayerWithRoles() {
        viewModelScope.launch {
            _playerList.value = playerDao.getAllPlayers()
            _gameRoleList.value = roleDao.getAllRoles()

            if (_gameRoleList.value.isEmpty()) {
                Log.e("matchPlayerWithRoles", "Rol listesi boş!")
                return@launch
            }

            _playerList.value.forEach { player ->
                val currentRole = _gameRoleList.value.random()
                _gameRoleList.value -= currentRole
                player.role = currentRole.name
                playerDao.updatePlayer(player)
            }
        }
    }

    fun saveRolesToDatabase(onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            roleDao.deleteAllRoles()

            _gameRoleList.value.forEach { role ->
                if (role.count > 0) {
                    repeat(role.count) {
                        roleDao.insertRole(role.copy(count = 1))
                    }
                }
            }

            onComplete()
        }
    }

    fun addRoleToRolesList(role: Roles) {
        viewModelScope.launch {
            val updatedList = _gameRoleList.value.toMutableList()
            val index = updatedList.indexOfFirst { it.name == role.name }

            if (index != -1) {
                updatedList[index] = updatedList[index].copy(count = updatedList[index].count + 1)
            } else {
                updatedList.add(role.copy(count = 1))
            }
            _gameRoleList.value = updatedList
            checkNavConditions()
        }
    }

    fun removeFromRolesList(role: Roles) {
        viewModelScope.launch {
            val updatedList = _gameRoleList.value.toMutableList()
            val index = updatedList.indexOfFirst { it.name == role.name }

            if (index != -1) {
                val current = updatedList[index]
                if (current.count > 1) {
                    updatedList[index] = current.copy(count = current.count - 1)
                } else {
                    updatedList.removeAt(index)
                }
            }
            _gameRoleList.value = updatedList
            checkNavConditions()
        }
    }

}