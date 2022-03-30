package com.example.communitymessages.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.communitymessages.domain.model.local.Attachment

@Entity(tableName = "timeline")
data class DatabaseMessage constructor(
    @PrimaryKey
    val id: String,
    val content: String,
    val score: Int,
    val title: String,
    val attachments: List<Attachment>
)