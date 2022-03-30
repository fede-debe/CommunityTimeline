package com.example.communitymessages.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.communitymessages.utils.DATABASE_NAME
import com.example.communitymessages.utils.type_converter.AttachmentConverter

@Database(entities = [DatabaseMessage::class], version = 1, exportSchema = false)
@TypeConverters(AttachmentConverter::class)
abstract class TimelineDatabase : RoomDatabase() {
    abstract fun timelineDao(): TimelineDao

    companion object {

        @Volatile
        private var INSTANCE: TimelineDatabase? = null

        fun getInstance(context: Context): TimelineDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TimelineDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}