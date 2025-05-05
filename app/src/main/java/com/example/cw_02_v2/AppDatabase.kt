package com.example.cw_02_v2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Film::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun filmDao():FilmDao
}