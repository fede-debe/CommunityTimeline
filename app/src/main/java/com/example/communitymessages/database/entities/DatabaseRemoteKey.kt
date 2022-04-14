package com.example.communitymessages.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.communitymessages.utils.DATABASE_REMOTE_KEYS

@Entity(tableName = DATABASE_REMOTE_KEYS)
data class DatabaseRemoteKey(
    @PrimaryKey
    val id: String,
    val prev: Int?,
    val next: Int?
)