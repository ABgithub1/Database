package com.example.database

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.database.room.AppDatabase

class DatabaseApplication : Application() {

    private var _appDatabase: AppDatabase? = null
    val appDatabase get() = requireNotNull(_appDatabase)

    override fun onCreate() {
        super.onCreate()
        _appDatabase = Room
            .databaseBuilder(
                this,
                AppDatabase::class.java,
                "database-room"
            )
            .allowMainThreadQueries()
            .build()
    }
}

val Context.appDatabase: AppDatabase
    get() = when (this) {
        is DatabaseApplication -> appDatabase
        else -> applicationContext.appDatabase
    }