package com.example.communitymessages.di

import android.content.Context
import androidx.room.Room
import com.example.communitymessages.database.TimelineDatabase
import com.example.communitymessages.utils.DATABASE_NAME
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
    fun buildDatabase(@ApplicationContext context: Context): TimelineDatabase {
        return Room.databaseBuilder(
            context,
            TimelineDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}