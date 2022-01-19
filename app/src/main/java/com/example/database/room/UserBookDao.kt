package com.example.database.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface UserBookDao {

    @Transaction
    @Query("SELECT * FROM RoomUser")
    fun getUsersWithBooks(): List<UserWithBooks>
}