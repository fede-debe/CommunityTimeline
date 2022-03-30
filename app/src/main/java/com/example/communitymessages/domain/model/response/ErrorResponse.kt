package com.example.communitymessages.domain.model.response

data class ErrorResponse(
    val code: Int,
    val message: String?
)