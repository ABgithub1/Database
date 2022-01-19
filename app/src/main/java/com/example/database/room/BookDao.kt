package com.example.database.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    fun getAll(): List<Book>

    @Insert
    fun insertBooks(vararg books: Book)

    @Delete
    fun deleteBook(book: Book)
}