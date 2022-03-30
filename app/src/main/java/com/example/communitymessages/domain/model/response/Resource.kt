package com.example.communitymessages.domain.model.response

data class Resource<out T>(val status: Status, val data: T?, val errorResponse: ErrorResponse?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(errorResponse: ErrorResponse?, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, errorResponse)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}