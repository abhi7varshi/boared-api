package com.example.boaredapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun postDao(): PostDao
}