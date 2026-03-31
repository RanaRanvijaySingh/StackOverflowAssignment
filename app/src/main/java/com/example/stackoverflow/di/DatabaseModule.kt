package com.example.stackoverflow.di

import android.content.Context
import androidx.room.Room
import com.example.stackoverflow.data.local.StackOverflowDatabase
import com.example.stackoverflow.data.local.daos.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideStackOverflowDatabase(
        @ApplicationContext context: Context
    ): StackOverflowDatabase =
        Room.databaseBuilder(
            context,
            StackOverflowDatabase::class.java,
            "stackoverflow.db"
        ).build()

    @Provides
    @Singleton
    fun provideUserDao(database: StackOverflowDatabase): UserDao =
        database.userDao()
}
