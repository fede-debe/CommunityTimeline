package com.example.communitymessages.data.network

import com.example.communitymessages.domain.model.response.TimelineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ContentService {

    @GET("companies/{id}/timeline")
    suspend fun getTimeline(
        @Path("id") id: String,
        @Query("page") page: String?
    ): List<TimelineResponse>

    @PUT("messages/{msg_id}/interested")
    suspend fun addToInterest(
        @Path("msg_id") msg_id: String,
    ): Response<Unit>

}