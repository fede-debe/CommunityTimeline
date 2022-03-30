package com.example.communitymessages.domain.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimelineResponse(
    val attachments: List<AttachmentResponse>,
    val content: String,
    val id: String,
    val score: Int,
    val title: String
)