package com.example.boaredapi.data.di

import android.content.Context
import androidx.room.Room
import com.example.boaredapi.data.local.AppDB
import com.example.boaredapi.data.local.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun providesAppDB(
        @ApplicationContext context: Context,
    ): AppDB {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDB::class.java,
            "posts_db"
        ).build()
    }

    @Provides
    fun providesPostDao(appDB: AppDB): PostDao {
        return appDB.postDao()
    }
}