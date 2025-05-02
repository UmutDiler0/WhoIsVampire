package com.example.whoisvampire.data.service


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.whoisvampire.data.model.Roles

@Dao
interface RoleDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertRole(role: Roles)

    @Delete
    suspend fun deleteRole(role: Roles)

    @Query("DELETE FROM Roles")
    suspend fun deleteAllRoles()

    @Query("SELECT * FROM Roles")
    suspend fun getAllRoles(): List<Roles>
}