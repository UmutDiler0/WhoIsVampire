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
    val roleDao: RoleDao
): ViewModel(){

    private var _rolesList = MutableStateFlow<List<Roles>>(roles.rolesList)
    val roleList: StateFlow<List<Roles>> get() = _rolesList

    private var _totalRoleNumber = MutableStateFlow<Int>(0)
    val totalRoleNumber: StateFlow<Int> get() = _totalRoleNumber

    private var _roleBound = MutableStateFlow<Int>(0)
    val roleBound: StateFlow<Int> get() = _roleBound

    private var _isNavAvailable = MutableStateFlow<Boolean>(false)
    val isNavAvailable: StateFlow<Boolean> get() = _isNavAvailable

    private var _selectedRoleList = MutableStateFlow<List<Roles>>(emptyList())

    private var _villagerCount = MutableStateFlow<Int>(0)

    private var _vampireCount = MutableStateFlow<Int>(0)

    init {
        viewModelScope.launch {
            _roleBound.value = playerDao.getAllPlayers().size
        }
    }

    fun insertRolesToRoom(){
        viewModelScope.launch {
            _selectedRoleList.value.forEach {
                roleDao.insertRole(it)
            }
        }
    }

    fun isNavAvailable(){
        viewModelScope.launch {
            if(_totalRoleNumber.value == _roleBound.value){
                _selectedRoleList.value.forEach {
                    if(it.name == "Vampire") _vampireCount.value  = it.countOfRoles
                    else if(it.name == "Villager") _villagerCount.value = it.countOfRoles
                }
                if(_vampireCount.value > _villagerCount.value){
                    _isNavAvailable.value = false
                }else{
                    _isNavAvailable.value = true
                }
            }else{
                _isNavAvailable.value = false
            }
        }
    }

    fun AddRoleToRolesList(role: Roles){
        viewModelScope.launch {
            roles.rolesList.find { it.name == role.name }?.let {
                it.countOfRoles++
            }
            _selectedRoleList.value = _selectedRoleList.value + role
            _totalRoleNumber.value++
            isNavAvailable()
        }
    }

    fun RemoveFromRolesList(role: Roles){
        viewModelScope.launch {
            roles.rolesList.find { it.name == role.name }?.let {
                it.countOfRoles--
            }
            _selectedRoleList.value = _selectedRoleList.value - role
            if (_totalRoleNumber.value > 0) _totalRoleNumber.value--
            isNavAvailable()
        }
    }

}