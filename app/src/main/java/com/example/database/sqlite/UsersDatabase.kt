package com.example.database.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.sqlite.transaction

class UsersDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(
            """
                CREATE TABLE $TABLE_NAME (
                    $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_NAME TEXT,
                    $COLUMN_CITY TEXT
                );
            """
        )
        // initial data
        database.execSQL(
            "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_CITY) VALUES ('Vlad', 'Minsk');"
        )
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(database)
    }

    fun getUsers(): List<SQLiteUser> = readableDatabase.transaction {
        query(TABLE_NAME, null, null, null, null, null, null)
            .use { cursor ->
                generateSequence { if (cursor.moveToNext()) cursor else null }
                    .map {
                        SQLiteUser(
                            id = it.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                            name = it.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                            city = it.getString(cursor.getColumnIndexOrThrow(COLUMN_CITY))
                        )
                    }
                    .toList()
            }
    }

    fun insertUser(user: SQLiteUser): SQLiteUser = writableDatabase.transaction {
        val values = ContentValues().apply {
            put(COLUMN_NAME, user.name)
            put(COLUMN_CITY, user.city)
        }
        val newRowId = insertWithOnConflict(
            TABLE_NAME,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )

        user.copy(id = newRowId)
    }

    fun updateUser(user: SQLiteUser) = writableDatabase.transaction {
        val values = ContentValues().apply {
            put(COLUMN_NAME, user.name)
            put(COLUMN_CITY, user.city)
        }
        updateWithOnConflict(
            TABLE_NAME,
            values,
            "$COLUMN_ID = ?",
            arrayOf(user.id.toString()),
            SQLiteDatabase.CONFLICT_REPLACE
        )
    }

    fun deleteUser(user: SQLiteUser) = writableDatabase.transaction {
        delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(user.id.toString()))
    }

    companion object {
        private const val DATABASE_NAME = "database-sqlite"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "users"

        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_CITY = "city"
    }
}