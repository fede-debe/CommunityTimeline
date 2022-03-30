package com.example.communitymessages.domain.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttachmentResponse(
    val id: String,
    @Json(name = "mime_type")
    val mimeType: String,
    val name: String,
    val size: Int,
    val url: String
)