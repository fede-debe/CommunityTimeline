package com.example.communitymessages.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.communitymessages.database.dao.RemoteKeyDao
import com.example.communitymessages.database.dao.TimelineDao
import com.example.communitymessages.database.entities.DatabaseMessage
import com.example.communitymessages.database.entities.DatabaseRemoteKey
import com.example.communitymessages.utils.type_converter.AttachmentConverter

@Database(
    entities = [
        DatabaseMessage::class,
        DatabaseRemoteKey::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(AttachmentConverter::class)
abstract class TimelineDatabase : RoomDatabase() {

    abstract fun getTimelineDao(): TimelineDao
    abstract fun getKeysDao(): RemoteKeyDao
}