package com.example.database.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomUser::class, Book::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): RoomUserDao

    abstract fun bookDao(): BookDao

    abstract fun userBookDao(): UserBookDao
}
