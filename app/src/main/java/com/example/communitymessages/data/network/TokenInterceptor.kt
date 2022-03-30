package com.example.communitymessages.data.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val tokenType: String, private val accessToken: String) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(
            request.newBuilder()
                .addHeader("Authorization", "$tokenType $accessToken")
                .build()
        )
    }
}