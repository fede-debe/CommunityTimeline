package com.example.communitymessages.domain.model

import com.example.communitymessages.database.entities.DatabaseMessage
import com.example.communitymessages.domain.model.local.Attachment
import com.example.communitymessages.domain.model.local.Message
import com.example.communitymessages.domain.model.response.TimelineResponse

fun DatabaseMessage.asUiModel() = Message(
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

fun TimelineResponse.asDatabaseModel() = DatabaseMessage(
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
    }
)


fun List<TimelineResponse>.asDatabaseModel(): Array<DatabaseMessage> {
    return map { timelineResponse ->
        DatabaseMessage(
            id = timelineResponse.id,
            title = timelineResponse.title,
            content = timelineResponse.content,
            score = timelineResponse.score,
            attachments = timelineResponse.attachments.map {
                Attachment(
                    it.id,
                    it.mimeType,
                    it.name,
                    it.size,
                    it.url
                )
            }
        )
    }.toTypedArray()
}