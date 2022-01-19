package com.example.database.room

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithBooks(
    @Embedded
    val user: RoomUser,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val books: List<Book>
)
