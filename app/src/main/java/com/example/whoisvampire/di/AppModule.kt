package com.example.whoisvampire.di

import android.content.Context
import androidx.room.Room
import com.example.whoisvampire.AppDatabase
import com.example.whoisvampire.data.service.PlayerDao
import com.example.whoisvampire.data.service.RoleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(context,AppDatabase::class.java,"app_database")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideDao(
        appDataBase: AppDatabase
    ): PlayerDao = appDataBase.playerDao()

    @Provides
    @Singleton
    fun provideRoleDao(
        appDatabase: AppDatabase
    ): RoleDao = appDatabase.roleDao()

    @Provides
    @Singleton
    fun provideSettingDao(
        appDatabase: AppDatabase
    ) = appDatabase.settingDao()

    @Singleton
    @Provides
    fun provideGameDao(
        appDatabase: AppDatabase
    ) = appDatabase.gameDao()

}