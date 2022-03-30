package com.example.communitymessages.data.network

import javax.inject.Inject

class ContentRemoteDataSource @Inject constructor(
    private val contentService: ContentService
) : BaseRemoteDataSource() {

    suspend fun addToInterest(id: String) = getResult {
        contentService.addToInterest(id)
    }
}