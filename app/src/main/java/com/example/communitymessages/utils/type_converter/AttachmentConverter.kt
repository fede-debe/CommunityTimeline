package com.example.communitymessages.utils.type_converter

import androidx.room.TypeConverter
import com.example.communitymessages.domain.model.local.Attachment
import org.json.JSONObject

class AttachmentConverter {
    @TypeConverter
    fun fromAttachment(attachment: Attachment): String {
        return JSONObject().apply {
            put("id", attachment.id)
            put("mimeType", attachment.mimeType)
            put("name", attachment.name)
            put("size", attachment.size)
            put("url", attachment.url)
        }.toString()
    }

    @TypeConverter
    fun toAttachment(attachment: String): Attachment {
        val json = JSONObject(attachment)
        return Attachment(
            json.getString("id"),
            json.getString("mimeType"),
            json.getString("name"),
            json.getInt("size"),
            json.getString("url")
        )
    }
}