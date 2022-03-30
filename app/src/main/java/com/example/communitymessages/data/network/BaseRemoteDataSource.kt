package com.example.communitymessages.data.network

import com.example.communitymessages.domain.model.response.ErrorResponse
import com.example.communitymessages.domain.model.response.Resource
import retrofit2.Response
import java.net.HttpURLConnection

abstract class BaseRemoteDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T?> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                return Resource.success(body)
            }
            return error(ErrorResponse(response.code(), response.message()))
        } catch (e: Exception) {
            return error(ErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, e.message))
        }
    }

    private fun <T> error(errorResponse: ErrorResponse): Resource<T> {
        return Resource.error(errorResponse)
    }
}