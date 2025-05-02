package com.example.whoisvampire.common.util

import com.example.whoisvampire.R
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.data.model.Roles
import com.example.whoisvampire.data.model.Settings


object roles{
    val rolesList = mutableListOf(
        Roles(
            name = "Villager",
            countOfRoles = 0
        ),
        Roles(
            name = "Vampire",
            countOfRoles = 0
        ),
        Roles(
            name = "Seeker",
            countOfRoles = 0
        ),
        Roles(
            name = "Doctor",
            countOfRoles = 0
        ),
        Roles(
            name = "Vampire Hunter",
            countOfRoles = 0
        ),
    )
}

object settingsList{
    val settingsList = mutableListOf(
        Settings(
            name = "Show Roles",
            isChecked = false
        ),
        Settings(
            name = "Show Timer",
            isChecked = false
        ),
        Settings(
            name = "First Night Kill",
            isChecked = false
        ),
        Settings(
            name = "Villager Night Action",
            isChecked = false
        ),
        Settings(
            name = "Skip Vote",
            isChecked = false
        ),
        Settings(
            name = "Show Vote Count",
            isChecked = false
        )
    )
}