package com.example.communitymessages.domain.model.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    val attachments: List<Attachment>,
    val content: String,
    val id: String,
    val score: Int,
    val title: String
) : Parcelable