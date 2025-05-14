package com.example.whoisvampire.ui.screens.roles

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whoisvampire.common.util.roles
import com.example.whoisvampire.data.model.Roles
import com.example.whoisvampire.data.service.PlayerDao
import com.example.whoisvampire.data.service.RoleDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RolesScreenViewModel @Inject constructor(
    val playerDao: PlayerDao,
    val roleDao: RoleDao,
): ViewModel(){

    private var _rolesList = MutableStateFlow<List<Roles>>(roles.rolesList)
    val roleList: StateFlow<List<Roles>> get() = _rolesList

    private var _totalRoleNumber = MutableStateFlow<Int>(0)
    val totalRoleNumber: StateFlow<Int> get() = _totalRoleNumber

    private var _roleBound = MutableStateFlow<Int>(0)
    val roleBound: StateFlow<Int> get() = _roleBound

    private var _isNavAvailable = MutableStateFlow<Boolean>(false)
    val isNavAvailable: StateFlow<Boolean> get() = _isNavAvailable

    private var _gameRoleList = MutableStateFlow<List<Roles>>(emptyList())
    val gameRoleList: StateFlow<List<Roles>> get() = _gameRoleList


    init {
        viewModelScope.launch {
            _roleBound.value = playerDao.getAllPlayers().size
        }
    }

    fun deleteAllPlayers(){
        viewModelScope.launch {
            playerDao.deleteAllPlayers()
            roleDao.deleteAllRoles()
        }
    }

    fun isNavAvailable(){
        if(_gameRoleList.value.size == _roleBound.value) {
            var villagerCount = 0
            var vampireCount = 0
            _gameRoleList.value.forEach {
                if(it.name == "Villager") villagerCount++
                else vampireCount++
            }
            if(villagerCount > vampireCount) _isNavAvailable.value = true
        }
    }

    fun AddRoleToRolesList(role: Roles){
        viewModelScope.launch{
            role.count++
           _gameRoleList.value += role
            roleDao.insertRole(role)
            _totalRoleNumber.value++
            isNavAvailable()
        }

    }

    fun RemoveFromRolesList(role: Roles){
        viewModelScope.launch{
            _gameRoleList.value -= role
            role.count--
            roleDao.deleteRole(role)
            if (_totalRoleNumber.value > 0) _totalRoleNumber.value--
            isNavAvailable()
        }
    }

}