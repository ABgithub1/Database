package com.example.database.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomUserDao {
    @Query("SELECT * FROM roomuser")
    fun getAll(): List<RoomUser>

    @Query("SELECT * FROM roomuser WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<RoomUser>

    @Query("SELECT * FROM roomuser WHERE name LIKE :name AND city LIKE :city LIMIT 1")
    fun findByNameAndCity(name: String, city: String): RoomUser

    @Insert
    fun insertAll(vararg users: RoomUser)

    @Delete
    fun delete(user: RoomUser)
}
