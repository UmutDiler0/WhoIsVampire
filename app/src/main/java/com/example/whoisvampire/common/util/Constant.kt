package com.example.whoisvampire.common.util

import com.example.whoisvampire.R
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.data.model.Roles
import com.example.whoisvampire.data.model.Settings


object roles{
    val rolesList = mutableListOf(
        Roles(
            name = "Köylü",
            count = 0,
            image = R.drawable.villager

        ),
        Roles(
            name = "Vampir",
            count = 0,
            image = R.drawable.vampir
        ),
        Roles(
            name = "Gözcü",
            count = 0,
            image = R.drawable.gozcu
        ),
        Roles(
            name = "Şifacı",
            count = 0,
            image = R.drawable.sifaci
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

object PlayerAvatar{
    val playerAvatar = listOf(
        R.drawable.oyuncu1,
        R.drawable.oyuncu2,
        R.drawable.oyuncu3,
        R.drawable.oyuncu4,
    )
}