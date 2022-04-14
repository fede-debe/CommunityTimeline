package com.example.communitymessages.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.communitymessages.domain.model.local.Attachment
import com.example.communitymessages.utils.DATABASE_NAME

@Entity(tableName = DATABASE_NAME)
data class DatabaseMessage(
    @PrimaryKey
    val id: String,
    val content: String,
    val score: Int,
    val title: String,
    val attachments: List<Attachment>
)