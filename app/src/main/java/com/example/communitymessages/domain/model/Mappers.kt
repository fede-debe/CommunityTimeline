package com.example.communitymessages.domain.model

import com.example.communitymessages.domain.model.local.Attachment
import com.example.communitymessages.domain.model.local.Message
import com.example.communitymessages.domain.model.response.TimelineResponse

fun TimelineResponse.asUiModel() = Message(
    id = this.id,
    title = this.title,
    content = this.content,
    score = this.score,
    attachments = this.attachments.map {
        Attachment(
            it.id,
            it.mimeType,
            it.name,
            it.size,
            it.url
        )
    })