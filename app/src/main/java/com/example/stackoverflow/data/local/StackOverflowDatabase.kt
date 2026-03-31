package com.example.stackoverflow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stackoverflow.data.local.daos.UserDao
import com.example.stackoverflow.data.local.entities.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StackOverflowDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
